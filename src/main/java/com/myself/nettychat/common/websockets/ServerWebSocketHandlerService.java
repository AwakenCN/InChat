package com.myself.nettychat.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
public abstract class ServerWebSocketHandlerService implements WebSocketHandlerApi {

    public abstract boolean login(Channel channel, Map<String,String> map);

    public abstract void sendMeText(Channel channel,Map<String,String> maps);

    public abstract void sendToText(Channel channel, Map<String,String> maps);

    public abstract void pong(Channel channel);

    public abstract void disconnect(Channel channel);

    public abstract void doTimeOut(Channel channel, IdleStateEvent evt);

}
