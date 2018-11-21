package com.myself.nettychat.common.properties;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:52 2018\11\21 0021
 */
@Slf4j
@Component
public class CacheTemplate {

    private final Map<String,Channel> cacheMap = new ConcurrentHashMap<>();

    public void add(String name,Channel channel){
        cacheMap.put(name, channel);
    }

    public Channel get(String name){
        return cacheMap.get(name);
    }
}
