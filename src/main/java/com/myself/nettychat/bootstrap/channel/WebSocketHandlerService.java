package com.myself.nettychat.bootstrap.channel;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.myself.nettychat.bootstrap.BaseApi;
import com.myself.nettychat.bootstrap.BaseAuthService;
import com.myself.nettychat.bootstrap.ChannelService;
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
public class WebSocketHandlerService extends ServerWebSocketHandlerService implements BaseApi {

    @Autowired
    InChatVerifyService inChatVerifyService;

    @Autowired
    ChannelService websocketChannelService;

    private final BaseAuthService baseAuthService;

    public WebSocketHandlerService(BaseAuthService baseAuthService){
        this.baseAuthService = baseAuthService;
    }

    @Override
    public boolean login(Channel channel, TextWebSocketFrame textWebSocketFrame) {
        //校验规则，自定义校验规则
        Map<String,String> maps = (Map<String, String>) JSON.parse(textWebSocketFrame.text());
        System.out.println("login-"+textWebSocketFrame.text());
        String token = maps.get("token");
        Gson gson = new Gson();
        Map<String,String> backMap = new HashMap<>();
        if (inChatVerifyService.verifyToken(token)){
            backMap.put("type","login");
            backMap.put("success","true");
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(backMap)));
            return true;
        }
        backMap.put("type","login");
        backMap.put("success","false");
        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(backMap)));
        close(channel);
        return false;
    }

    @Override
    public void sendText(Channel channel, TextWebSocketFrame webSocketFrame) {
        System.out.println("sendText-"+webSocketFrame.text());
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
