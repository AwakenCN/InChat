package com.github.unclecatmyself.common.websockets;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * 业务层伪接口
 * Created by MySelf on 2018/11/21.
 */
public abstract class ServerWebSocketHandlerService implements WebSocketHandlerApi {

    /**
     * 登录类型
     * @param channel {@link Channel} 链接实例
     * @param map {@link Map} 数据信息
     * @return {@link Boolean} 成功失败
     */
    public abstract boolean login(Channel channel, Map<String,Object> map);

    /**
     * 发送给自己
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendMeText(Channel channel,Map<String,Object> maps);

    /**
     * 发送给某人
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendToText(Channel channel, Map<String,Object> maps);

    /**
     * 发送给群聊
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void sendGroupText(Channel channel, Map<String, Object> maps);

    /**
     * 登录校验
     * @param channel {@link Channel} 链接实例
     * @param maps {@link Map} 数据信息
     */
    public abstract void verify(Channel channel, Map<String, Object> maps);
}
