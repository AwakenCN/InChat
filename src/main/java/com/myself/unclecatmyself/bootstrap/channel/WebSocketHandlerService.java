package com.myself.unclecatmyself.bootstrap.channel;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.myself.unclecatmyself.bootstrap.backmsg.InChatBackMapService;
import com.myself.unclecatmyself.bootstrap.WsChannelService;
import com.myself.unclecatmyself.common.utils.ConstansUtil;
import com.myself.unclecatmyself.common.websockets.ServerWebSocketHandlerService;
import com.myself.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.myself.unclecatmyself.task.DataAsynchronousTask;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
@Component
public class WebSocketHandlerService extends ServerWebSocketHandlerService{

    @Autowired
    InChatVerifyService inChatVerifyService;

    @Autowired
    InChatBackMapService inChatBackMapService;

    @Autowired
    WsChannelService websocketChannelService;

    @Autowired
    DataAsynchronousTask dataAsynchronousTask;

    private final Gson gson;

    public WebSocketHandlerService(Gson gson){
        this.gson = gson;
    }

    @Override
    public boolean login(Channel channel, Map<String,Object> maps) {
        //校验规则，自定义校验规则
        return check(channel, maps);
    }

    @Override
    public void sendMeText(Channel channel, Map<String,Object> maps) {
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
        maps.put(ConstansUtil.ON_ONLINE,no_online);
        try {
            dataAsynchronousTask.writeData(maps);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void verify(Channel channel, Map<String, Object> maps) {
        String token = (String) maps.get(ConstansUtil.TOKEN);
        System.out.println(token);
        if (inChatVerifyService.verifyToken(token)){
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
            close(channel);
        }
    }

    private Boolean check(Channel channel, Map<String, Object> maps){
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
