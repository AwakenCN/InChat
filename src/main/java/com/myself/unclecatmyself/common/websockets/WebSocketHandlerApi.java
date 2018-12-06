package com.myself.unclecatmyself.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
public interface WebSocketHandlerApi {

    void close(Channel channel);

    void sendMeText(Channel channel, Map<String,Object> maps);

    void sendToText(Channel channel, Map<String,Object> maps);
}
