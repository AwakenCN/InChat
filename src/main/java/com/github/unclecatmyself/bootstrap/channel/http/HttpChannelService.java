package com.github.unclecatmyself.bootstrap.channel.http;

import io.netty.channel.Channel;

/**
 * Create by UncleCatMySelf in 11:41 2018\12\31 0031
 */
public interface HttpChannelService {

    void getSize(Channel channel);

    void sendFromServer(Channel channel,String token);

    void notFindUri(Channel channel);

    void close(Channel channel);

    void getList(Channel channel);

}
