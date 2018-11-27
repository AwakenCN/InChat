package com.myself.nettychat.bootstrap.channel;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.myself.nettychat.backmsg.InChatBackMapService;
import com.myself.nettychat.bootstrap.BaseApi;
import com.myself.nettychat.bootstrap.BaseAuthService;
import com.myself.nettychat.bootstrap.ChannelService;
import com.myself.nettychat.bootstrap.WsChannelService;
import com.myself.nettychat.common.websockets.ServerWebSocketHandlerService;
import com.myself.nettychat.verify.InChatVerifyService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
@Slf4j
@Component
public class WebSocketHandlerService extends ServerWebSocketHandlerService{

    @Autowired
    InChatVerifyService inChatVerifyService;

    @Autowired
    InChatBackMapService inChatBackMapService;

    @Autowired
    WsChannelService websocketChannelService;

    private final Gson gson;

    private final BaseAuthService baseAuthService;

    public WebSocketHandlerService(BaseAuthService baseAuthService,Gson gson){
        this.baseAuthService = baseAuthService;
        this.gson = gson;
    }

    @Override
    public boolean login(Channel channel, Map<String,String> maps) {
        //校验规则，自定义校验规则
        String token = maps.get(inChatVerifyService.getVerifyLogin());
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
    public void sendMeText(Channel channel, Map<String,String> maps) {
        channel.writeAndFlush(new TextWebSocketFrame(
                gson.toJson(inChatBackMapService.sendMe(maps.get("value")))));
    }

    @Override
    public void sendToText(Channel channel, Map<String, String> maps) {
        String otherOne = maps.get("one");
        String value = maps.get("value");
        String me = maps.get("me");
        if (websocketChannelService.hasOther(otherOne)){
            //发送给对方
            Channel other = websocketChannelService.getChannel(otherOne);
            other.writeAndFlush(new TextWebSocketFrame(
                    gson.toJson(inChatBackMapService.getMsg(me,value))));
            //返回给自己
            channel.writeAndFlush(new TextWebSocketFrame(
                    gson.toJson(inChatBackMapService.sendBack(otherOne,value))));
        }
    }


    @Override
    public void pong(Channel channel) {
        log.info("【pong】"+channel.remoteAddress());

    }

    @Override
    public void disconnect(Channel channel) {
        log.info("【disconnect】"+channel.remoteAddress());
    }

    @Override
    public void doTimeOut(Channel channel, IdleStateEvent evt) {
        log.info("【PingPongService：doTimeOut 心跳超时】" + channel.remoteAddress() + "【channel 关闭】");

    }

    @Override
    public void close(Channel channel) {
        log.info("【close】"+channel.remoteAddress());
        channel.close();
    }
}
