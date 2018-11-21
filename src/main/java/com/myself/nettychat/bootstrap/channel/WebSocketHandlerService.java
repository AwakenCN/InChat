package com.myself.nettychat.bootstrap.channel;

import com.myself.nettychat.bootstrap.BaseApi;
import com.myself.nettychat.bootstrap.BaseAuthService;
import com.myself.nettychat.bootstrap.ChannelService;
import com.myself.nettychat.common.websockets.ServerWebSocketHandlerService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by MySelf on 2018/11/21.
 */
@Slf4j
@Component
public class WebSocketHandlerService extends ServerWebSocketHandlerService implements BaseApi {

    @Autowired
    ChannelService websocketChannelService;

    private final BaseAuthService baseAuthService;

    public WebSocketHandlerService(BaseAuthService baseAuthService){
        this.baseAuthService = baseAuthService;
    }

    @Override
    public boolean login(Channel channel, TextWebSocketFrame textWebSocketFrame) {
        //校验规则，自定义校验规则
        System.out.println("login-"+textWebSocketFrame.text());
        return true;
    }

    @Override
    public void sendText(Channel channel, TextWebSocketFrame webSocketFrame) {
        System.out.println("sendText-"+webSocketFrame.text());
    }

    @Override
    public void pong(Channel channel) {

    }

    @Override
    public void disconnect(Channel channel) {

    }

    @Override
    public void doTimeOut(Channel channel, IdleStateEvent evt) {
        log.info("【PingPongService：doTimeOut 心跳超时】" + channel.remoteAddress() + "【channel 关闭】");

    }

    @Override
    public void close(Channel channel) {

    }
}
