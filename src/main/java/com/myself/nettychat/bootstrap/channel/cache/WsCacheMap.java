package com.myself.nettychat.bootstrap.channel.cache;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 * Created by MySelf on 2018/11/26.
 */
@Slf4j
@Component
public class WsCacheMap {

    Map<String,Channel> maps = new ConcurrentHashMap<String,Channel>();

    /**
     * 存储链接
     * @param token {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public void saveWs(String token,Channel channel){
        log.info("【新增用户链接实例：】"+token);
        maps.put(token,channel);
    }

    /**
     * 获取链接数据
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public Channel getByToken(String token){
        log.info("【获取用户链接实例：】"+token);
        return maps.get(token);
    }

    /**
     * 删除链接数据
     * @param token {@link String} 用户标识
     */
    public void deleteWs(String token){
        log.info("【删除用户链接实例：】"+token);
        maps.remove(token);
    }

    /**
     * 获取链接数
     * @return {@link Integer} 链接数
     */
    public Integer getSize(){
        return maps.size();
    }

    /**
     * 判断是否存在链接账号
     * @param token {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public boolean hasToken(String token){
        return maps.containsKey(token);
    }

}
