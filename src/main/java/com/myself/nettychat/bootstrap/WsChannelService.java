package com.myself.nettychat.bootstrap;

import io.netty.channel.Channel;

/**
 * WebSocket 聊天业务消息处理
 * Created by MySelf on 2018/11/26.
 */
public interface WsChannelService {

    void loginWsSuccess(Channel channel, String token);

}
