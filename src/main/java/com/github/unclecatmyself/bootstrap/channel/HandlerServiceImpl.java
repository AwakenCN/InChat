package com.github.unclecatmyself.bootstrap.channel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.unclecatmyself.bootstrap.backmsg.InChatBackMapService;
import com.github.unclecatmyself.bootstrap.backmsg.InChatBackMapServiceImpl;
import com.github.unclecatmyself.bootstrap.channel.cache.WsCacheMap;
import com.github.unclecatmyself.bootstrap.channel.http.HttpChannelService;
import com.github.unclecatmyself.bootstrap.channel.http.HttpChannelServiceImpl;
import com.github.unclecatmyself.bootstrap.channel.ws.WebSocketChannelService;
import com.github.unclecatmyself.common.base.HandlerService;
import com.github.unclecatmyself.common.bean.SendInChat;
import com.github.unclecatmyself.common.bean.vo.SendServerVO;
import com.github.unclecatmyself.common.constant.Constans;
import com.github.unclecatmyself.task.TextData;
import com.google.gson.Gson;
import com.github.unclecatmyself.bootstrap.channel.ws.WsChannelService;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.task.DataAsynchronousTask;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
public class HandlerServiceImpl extends HandlerService {

    private final InChatVerifyService inChatVerifyService;

    private final InChatBackMapService inChatBackMapService = new InChatBackMapServiceImpl();

    private final HttpChannelService httpChannelService = new HttpChannelServiceImpl();

    private final WsChannelService websocketChannelService = new WebSocketChannelService();

    private DataAsynchronousTask dataAsynchronousTask;

    private TextData textData;

    public HandlerServiceImpl(DataAsynchronousTask dataAsynchronousTask,InChatVerifyService inChatVerifyService,TextData textData) {
        this.dataAsynchronousTask = dataAsynchronousTask;
        this.inChatVerifyService = inChatVerifyService;
        this.textData = textData;
    }


    @Override
    public void getList(Channel channel) {
        httpChannelService.getList(channel);
    }

    @Override
    public void getSize(Channel channel) {
        httpChannelService.getSize(channel);
    }

    @Override
    public void sendFromServer(Channel channel, SendServerVO serverVO) {
        httpChannelService.sendFromServer(channel,serverVO);
    }

    @Override
    public void sendInChat(Channel channel, FullHttpMessage msg) {
        System.out.println(msg);
        String content = msg.content().toString(CharsetUtil.UTF_8);
        Gson gson = new Gson();
        SendInChat sendInChat = gson.fromJson(content,SendInChat.class);
        httpChannelService.sendByInChat(channel,sendInChat);
    }

    @Override
    public void notFindUri(Channel channel) {
        httpChannelService.notFindUri(channel);
    }

    @Override
    public boolean login(Channel channel, Map<String,Object> maps) {
        //校验规则，自定义校验规则
        return check(channel, maps);
    }

    @Override
    public void sendMeText(Channel channel, Map<String,Object> maps) {
        Gson gson = new Gson();
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendMe((String) maps.get(Constans.VALUE))))).addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()){
                            dataAsynchronousTask.writeData(maps);
                            textData.writeData(maps);
                        } else {
                            future.cause().printStackTrace();
                            future.channel().close();
                        }
                    }
                }
        );
//        try {
//            dataAsynchronousTask.writeData(maps);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void sendToText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String otherOne = (String) maps.get(Constans.ONE);
        String value = (String) maps.get(Constans.VALUE);
        String token = (String) maps.get(Constans.TOKEN);
        //返回给自己
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendBack(otherOne,value))));
        if (websocketChannelService.hasOther(otherOne)){
            //发送给对方--在线
            Channel other = websocketChannelService.getChannel(otherOne);
            if (other == null){
                //转http分布式
                httpChannelService.sendInChat(otherOne,inChatBackMapService.getMsg(token,value));
            }else{
                other.writeAndFlush(new TextWebSocketFrame(
                        gson.toJson(inChatBackMapService.getMsg(token,value))));
            }
        }else {
            maps.put(Constans.ON_ONLINE,otherOne);
        }
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void sendGroupText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String groupId = (String) maps.get(Constans.GROUPID);
        String token = (String) maps.get(Constans.TOKEN);
        String value = (String) maps.get(Constans.VALUE);
        List<String> no_online = new ArrayList<>();
        JSONArray array = inChatVerifyService.getArrayByGroupId(groupId);
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
        for (Object item:array) {
            if (!token.equals(item)){
                if (websocketChannelService.hasOther((String) item)){
                    Channel other = websocketChannelService.getChannel((String) item);
                    if (other == null){
                        //转http分布式
                        httpChannelService.sendInChat((String) item,inChatBackMapService.sendGroup(token,value,groupId));
                    }else{
                        other.writeAndFlush(new TextWebSocketFrame(
                                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
                    }
                }else{
                    no_online.add((String) item);
                }
            }
        }
        maps.put(Constans.ONLINE_GROUP,no_online);
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void verify(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String token = (String) maps.get(Constans.TOKEN);
        System.out.println(token);
        if (inChatVerifyService.verifyToken(token)){
            return;
        }else{
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
            close(channel);
        }
    }

    @Override
    public void sendPhotoToMe(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        System.out.println(maps.get(Constans.VALUE));
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendMe((String) maps.get(Constans.VALUE)))));
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean check(Channel channel, Map<String, Object> maps){
        Gson gson = new Gson();
        String token = (String) maps.get(Constans.TOKEN);
        if (inChatVerifyService.verifyToken(token)){
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginSuccess())));
            websocketChannelService.loginWsSuccess(channel,token);
            return true;
        }
        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
        close(channel);
        return false;
    }

    @Override
    public void close(Channel channel) {
        websocketChannelService.close(channel);
    }
}
