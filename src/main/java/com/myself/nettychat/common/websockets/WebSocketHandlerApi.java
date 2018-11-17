package com.myself.nettychat.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:22 2018\11\16 0016
 */
public interface WebSocketHandlerApi {

    void close(Channel channel);

    void sendText(Channel channel, TextWebSocketFrame webSocketFrame);

    void doTimeOut(Channel channel, IdleStateEvent evt);

}
