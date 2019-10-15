package com.github.unclecatmyself.bootstrap.handler;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
public interface Handler {

    void close(Channel channel);

}
