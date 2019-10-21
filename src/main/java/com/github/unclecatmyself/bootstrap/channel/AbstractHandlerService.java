package com.github.unclecatmyself.bootstrap.channel;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.channel.protocol.Response;
import com.github.unclecatmyself.core.constant.StateConstant;
import com.github.unclecatmyself.support.InChatResponse;
import com.github.unclecatmyself.bootstrap.channel.protocol.HttpChannel;
import com.github.unclecatmyself.bootstrap.channel.http.HttpChannelImpl;
import com.github.unclecatmyself.bootstrap.channel.ws.WebSocketChannel;
import com.github.unclecatmyself.support.HandlerService;
import com.github.unclecatmyself.core.bean.SendInChat;
import com.github.unclecatmyself.core.bean.vo.SendServerVO;
import com.github.unclecatmyself.core.constant.Constants;
import com.github.unclecatmyself.scheduling.AsyncListener;
import com.google.gson.Gson;
import com.github.unclecatmyself.bootstrap.channel.protocol.SocketChannel;
import com.github.unclecatmyself.bootstrap.channel.protocol.InChatVerifyService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
public class AbstractHandlerService extends HandlerService {

    private final InChatVerifyService inChatVerifyService;

    private final Response response = new InChatResponse();

    private final HttpChannel httpChannel = new HttpChannelImpl();

    private final SocketChannel webSocketChannel = new WebSocketChannel();

    private AsyncListener asyncListener;

    public AbstractHandlerService(InChatVerifyService inChatVerifyService, AsyncListener asyncListener) {
        this.inChatVerifyService = inChatVerifyService;
        this.asyncListener = asyncListener;
    }


    @Override
    public void getList(Channel channel) {
        httpChannel.getList(channel);
    }

    @Override
    public void getSize(Channel channel) {
        httpChannel.getSize(channel);
    }

    @Override
    public void getState(Channel channel, SendServerVO sendServerVO) {
        httpChannel.getState(channel, sendServerVO);
    }

    @Override
    public void sendFromServer(Channel channel, SendServerVO serverVO) {
        httpChannel.sendFromServer(channel, serverVO);
    }

    @Override
    public void sendInChat(Channel channel, FullHttpMessage msg) {
        System.out.println(msg);
        String content = msg.content().toString(CharsetUtil.UTF_8);
        Gson gson = new Gson();
        SendInChat sendInChat = gson.fromJson(content, SendInChat.class);
        httpChannel.sendByInChat(channel, sendInChat);
    }

    @Override
    public void notFindUri(Channel channel) {
        httpChannel.notFindUri(channel);
    }

    @Override
    public boolean login(Channel channel, Map<String, Object> maps) {
        //校验规则，自定义校验规则
        return check(channel, maps);
    }

    @Override
    public void sendMeText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(response.sendMe((String) maps.get(Constants.VALUE)))));
        maps.put(Constants.ONLINE, Constants.TRUE);
        asyncListener.asyncData(maps);
    }

    @Override
    public void sendToText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String otherOne = (String) maps.get(Constants.ONE);
        String value = (String) maps.get(Constants.VALUE);
        String token = (String) maps.get(Constants.TOKEN);
        //返回给自己
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(response.sendBack(otherOne, value))));
        if (webSocketChannel.hasOther(otherOne)) {
            //发送给对方--在线
            Channel other = webSocketChannel.getChannel(otherOne);
            if (other == null) {
                //转http分布式
                httpChannel.sendInChat(otherOne, response.getMessage(token, value));
            } else {
                other.writeAndFlush(new TextWebSocketFrame(
                        gson.toJson(response.getMessage(token, value))));
                maps.put(Constants.ONLINE, Constants.TRUE);
            }
        } else {
            maps.put(Constants.ONLINE, Constants.FALSE);
        }
        asyncListener.asyncData(maps);
    }

    @Override
    public void sendGroupText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String groupId = (String) maps.get(Constants.GROUP_ID);
        String token = (String) maps.get(Constants.TOKEN);
        String value = (String) maps.get(Constants.VALUE);
        String no_online = "";
        JSONArray array = inChatVerifyService.getArrayByGroupId(groupId);
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(response.sendGroup(token, value, groupId))));
        for (Object item : array) {
            if (!token.equals(item)) {
                if (webSocketChannel.hasOther((String) item)) {
                    Channel other = webSocketChannel.getChannel((String) item);
                    if (other == null) {
                        //转http分布式
                        httpChannel.sendInChat((String) item, response.sendGroup(token, value, groupId));
                    } else {
                        other.writeAndFlush(new TextWebSocketFrame(
                                gson.toJson(response.sendGroup(token, value, groupId))));
                    }
                } else {
                    no_online = (String) item + "、" + no_online;
                }
            }
        }
        maps.put(Constants.ONLINE_GROUP, no_online.substring(0, no_online.length() - 1));
        asyncListener.asyncData(maps);
    }

    @Override
    public void verify(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String token = (String) maps.get(Constants.TOKEN);
        System.out.println(token);
        if (inChatVerifyService.verifyToken(token)) {
            return;
        } else {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(response.loginError())));
            close(channel);
        }
    }

    @Override
    public void sendPhotoToMe(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        System.out.println(maps.get(Constants.VALUE));
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(response.sendMe((String) maps.get(Constants.VALUE)))));
        asyncListener.asyncData(maps);
    }

    private Boolean check(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String token = (String) maps.get(Constants.TOKEN);
        if (inChatVerifyService.verifyToken(token)) {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(response.loginSuccess())));
            webSocketChannel.loginWsSuccess(channel, token);
            asyncListener.asyncState(StateConstant.ONLINE, token);
            return true;
        }
        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(response.loginError())));
        close(channel);
        return false;
    }

    @Override
    public void close(Channel channel) {
        String token = webSocketChannel.close(channel);
        asyncListener.asyncState(StateConstant.LINE, token);
    }
}
