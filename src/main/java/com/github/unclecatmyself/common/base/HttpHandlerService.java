package com.github.unclecatmyself.common.base;

import io.netty.channel.Channel;

/**
 * Create by UncleCatMySelf in 22:02 2018\12\30 0030
 */
public abstract class HttpHandlerService implements HandlerApi {

    public abstract void getSize(Channel channel);

    public abstract void sendFromServer(Channel channel,String token);

    public abstract void notFindUri(Channel channel);
}
