package com.myself.nettychat.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by MySelf on 2018/11/21.
 */
public abstract class ServerWebSocketHandlerService implements WebSocketHandlerApi {

    public abstract boolean login(Channel channel, TextWebSocketFrame textWebSocketFrame);

    public abstract void sendText(Channel channel, TextWebSocketFrame webSocketFrame);

    public abstract void pong(Channel channel);

    public abstract void disconnect(Channel channel);

    public abstract void doTimeOut(Channel channel, IdleStateEvent evt);

}
