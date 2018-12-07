package com.github.unclecatmyself.bootstrap.channel.cache;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 * Created by MySelf on 2018/11/26.
 */
@Component
public class WsCacheMap {

    /**
     * 存储用户标识与链接实例
     */
    Map<String,Channel> maps = new ConcurrentHashMap<String,Channel>();

    /**
     * 存储链接地址与用户标识
     */
    Map<String,String> addMaps = new ConcurrentHashMap<>();

    /**
     * 存储链接
     * @param token {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public void saveWs(String token,Channel channel){
        maps.put(token,channel);
    }

    /**
     * 存储登录信息
     * @param address 登录地址
     * @param token 用户标签
     */
    public void saveAd(String address,String token){
        addMaps.put(address, token);
    }

    /**
     * 获取链接数据
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public Channel getByToken(String token){
        return maps.get(token);
    }

    /**
     * 获取对应token标签
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public String getByAddress(String address){
        return addMaps.get(address);
    }

    /**
     * 删除链接数据
     * @param token {@link String} 用户标识
     */
    public void deleteWs(String token){
        maps.remove(token);
    }

    /**
     * 删除链接地址
     * @param address
     */
    public void deleteAd(String address){
        addMaps.remove(address);
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
