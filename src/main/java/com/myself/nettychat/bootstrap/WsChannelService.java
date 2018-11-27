package com.myself.nettychat.bootstrap;

import io.netty.channel.Channel;

/**
 * WebSocket 聊天业务消息处理
 * Created by MySelf on 2018/11/26.
 */
public interface WsChannelService {

    /**
     * 登录成功存储到本地缓存
     * @param channel {@link Channel} 链接实例
     * @param token {@link String} 用户标识
     */
    void loginWsSuccess(Channel channel, String token);

    /**
     * 判断是否存在当前在线用户
     * @param otherOne {@link String} 某人的用户标识
     * @return {@link Boolean} 是否存在
     */
    boolean hasOther(String otherOne);

    /**
     * 获取某人的链接实例
     * @param otherOne {@link String} 用户唯一标识
     * @return {@link Channel} 链接实例
     */
    Channel getChannel(String otherOne);
}
