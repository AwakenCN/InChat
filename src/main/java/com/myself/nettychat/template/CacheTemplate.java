package com.myself.nettychat.template;

import com.myself.nettychat.dto.CacheDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:46 2018\11\8 0008
 */
@Component
public class CacheTemplate {

    /**存放链接池实例*/
    private Map<String,Object> ChannelRedisMap = new ConcurrentHashMap<>();
    /**用户与柜子与锁关系*/
    private Map<String,List<CacheDTO>> DTOMap = new ConcurrentHashMap<>();

    /**
     * 存储对应的用户名与Netty链接实例
     * @param name 登录用户名
     * @param channel Netty链接实例
     */
    public void saveChannel(String name,Object channel){
        ChannelRedisMap.put(name,channel);
    }

    /**
     * 获取存储池中的链接实例
     * @param name 登录用户名
     * @return {@link io.netty.channel.Channel 链接实例}
     */
    public Object getChannel(String name){
        return ChannelRedisMap.get(name);
    }

    public void saveDTO(String channelId,List<CacheDTO> cacheDTOS){
        System.out.println("添加"+channelId);
        DTOMap.put(channelId, cacheDTOS);
    }

    public List<CacheDTO> getDTO(String channelId){
        return DTOMap.get(channelId);
    }

    public boolean hasCacheDTO(String channelId){
        return DTOMap.containsKey(channelId);
    }

}
