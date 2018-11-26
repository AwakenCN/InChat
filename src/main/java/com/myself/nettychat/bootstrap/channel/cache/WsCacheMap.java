package com.myself.nettychat.bootstrap.channel.cache;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by MySelf on 2018/11/26.
 */
@Slf4j
@Component
public class WsCacheMap {

    Map<String,Channel> maps = new ConcurrentHashMap<String,Channel>();

    public void saveWs(String token,Channel channel){
        log.info("【新增用户链接：】"+token);
        maps.put(token,channel);
    }

    public Channel getByToken(String token){
        log.info("【获取用户链接：】"+token);
        return maps.get(token);
    }

    public void deleteWs(String token){
        log.info("【删除用户链接：】"+token);
        maps.remove(token);
    }

}
