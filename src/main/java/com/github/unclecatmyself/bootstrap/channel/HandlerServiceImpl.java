package com.github.unclecatmyself.bootstrap.channel;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.backmsg.InChatBackMapService;
import com.github.unclecatmyself.bootstrap.backmsg.InChatBackMapServiceImpl;
import com.github.unclecatmyself.bootstrap.channel.cache.WsCacheMap;
import com.github.unclecatmyself.bootstrap.channel.http.HttpChannelService;
import com.github.unclecatmyself.bootstrap.channel.http.HttpChannelServiceImpl;
import com.github.unclecatmyself.bootstrap.channel.ws.WebSocketChannelService;
import com.github.unclecatmyself.common.base.HandlerService;
import com.github.unclecatmyself.common.bean.vo.GetSizeVO;
import com.github.unclecatmyself.common.bean.vo.NotFindUriVO;
import com.github.unclecatmyself.common.bean.vo.ResultVO;
import com.google.gson.Gson;
import com.github.unclecatmyself.bootstrap.channel.ws.WsChannelService;
import com.github.unclecatmyself.common.utils.ConstansUtil;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.task.DataAsynchronousTask;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
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

    private final DataAsynchronousTask dataAsynchronousTask;

    public HandlerServiceImpl(DataAsynchronousTask dataAsynchronousTask,InChatVerifyService inChatVerifyService) {
        this.dataAsynchronousTask = dataAsynchronousTask;
        this.inChatVerifyService = inChatVerifyService;
    }


    @Override
    public void getSize(Channel channel) {
        httpChannelService.getSize(channel);
    }

    @Override
    public void sendFromServer(Channel channel, String token) {
        //未实现
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
                gson.toJson(inChatBackMapService.sendMe((String) maps.get(ConstansUtil.VALUE)))));
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendToText(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String otherOne = (String) maps.get(ConstansUtil.ONE);
        String value = (String) maps.get(ConstansUtil.VALUE);
        String token = (String) maps.get(ConstansUtil.TOKEN);
        //返回给自己
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendBack(otherOne,value))));
        if (websocketChannelService.hasOther(otherOne)){
            //发送给对方--在线
            Channel other = websocketChannelService.getChannel(otherOne);
            other.writeAndFlush(new TextWebSocketFrame(
                    gson.toJson(inChatBackMapService.getMsg(token,value))));
        }else {
            maps.put(ConstansUtil.ON_ONLINE,otherOne);
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
        String groupId = (String) maps.get(ConstansUtil.GROUPID);
        String token = (String) maps.get(ConstansUtil.TOKEN);
        String value = (String) maps.get(ConstansUtil.VALUE);
        List<String> no_online = new ArrayList<>();
        JSONArray array = inChatVerifyService.getArrayByGroupId(groupId);
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
        for (Object item:array) {
            if (!token.equals(item)){
                if (websocketChannelService.hasOther((String) item)){
                    Channel other = websocketChannelService.getChannel((String) item);
                    other.writeAndFlush(new TextWebSocketFrame(
                            gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
                }else{
                    no_online.add((String) item);
                }
            }
        }
        maps.put(ConstansUtil.ONLINE_GROUP,no_online);
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void verify(Channel channel, Map<String, Object> maps) {
        Gson gson = new Gson();
        String token = (String) maps.get(ConstansUtil.TOKEN);
        System.out.println(token);
        if (inChatVerifyService.verifyToken(token)){
            return;
        }else{
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
            close(channel);
        }
    }

    private Boolean check(Channel channel, Map<String, Object> maps){
        Gson gson = new Gson();
        String token = (String) maps.get(ConstansUtil.TOKEN);
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
