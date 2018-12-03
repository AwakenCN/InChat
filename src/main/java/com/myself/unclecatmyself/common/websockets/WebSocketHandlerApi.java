package com.myself.unclecatmyself.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:22 2018\11\16 0016
 */
public interface WebSocketHandlerApi {

    void close(Channel channel);

    void sendMeText(Channel channel, Map<String,Object> maps);

    void sendToText(Channel channel, Map<String,Object> maps);

    void doTimeOut(Channel channel, IdleStateEvent evt);

    void addGroup(Channel channel, Map<String, Object> maps);
}
