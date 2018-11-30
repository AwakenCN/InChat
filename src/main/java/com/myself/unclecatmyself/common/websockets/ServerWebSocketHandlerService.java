package com.myself.unclecatmyself.common.websockets;

import io.netty.channel.Channel;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/21.
 */
public abstract class ServerWebSocketHandlerService implements WebSocketHandlerApi {

    /**
     * 登录类型
     * @param channel {@link Channel} 链接实例
     * @param map {@link Map} 数据信息
     * @return {@link Boolean} 成功失败
     */
    public abstract boolean login(Channel channel, Map<String,String> map);

    /**
     * 发送给自己
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendMeText(Channel channel,Map<String,String> maps);

    /**
     * 发送给某人
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendToText(Channel channel, Map<String,String> maps);

    public abstract void pong(Channel channel);

    /**
     * 主动关闭
     * @param channel {@link Channel} 链接实例
     */
    public abstract void disconnect(Channel channel);

    public abstract void doTimeOut(Channel channel, IdleStateEvent evt);

    /**
     * 发送给群聊
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendGroupText(Channel channel, Map<String, String> maps);
}
