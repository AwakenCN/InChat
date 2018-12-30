package com.github.unclecatmyself.bootstrap.channel.cache;

import com.github.unclecatmyself.exception.NotFindLoginChannlException;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 * Created by MySelf on 2018/11/26.
 */
public class WsCacheMap {

    /**
     * 存储用户标识与链接实例
     */
    private final static Map<String,Channel> maps = new ConcurrentHashMap<String,Channel>();

    /**
     * 存储链接地址与用户标识
     */
    private final static Map<String,String> addMaps = new ConcurrentHashMap<>();

    /**
     * 存储链接
     * @param token {@link String} 用户标签
     * @param channel {@link Channel} 链接实例
     */
    public static void saveWs(String token,Channel channel){
        maps.put(token,channel);
    }

    /**
     * 存储登录信息
     * @param address 登录地址
     * @param token 用户标签
     */
    public static void saveAd(String address,String token){
        addMaps.put(address, token);
    }

    /**
     * 获取链接数据
     * @param token {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public static Channel getByToken(String token){
        return maps.get(token);
    }

    /**
     * 获取对应token标签
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public static String getByAddress(String address){
        return addMaps.get(address);
    }

    /**
     * 删除链接数据
     * @param token {@link String} 用户标识
     */
    public static void deleteWs(String token){
        try {
            maps.remove(token);
        }catch (NullPointerException e){
            throw new NotFindLoginChannlException("未找到正常注册的连接");
        }
    }

    /**
     * 删除链接地址
     * @param address
     */
    public static void deleteAd(String address){
        addMaps.remove(address);
    }

    /**
     * 获取链接数
     * @return {@link Integer} 链接数
     */
    public static Integer getSize(){
        return maps.size();
    }

    /**
     * 判断是否存在链接账号
     * @param token {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public static boolean hasToken(String token){
        return maps.containsKey(token);
    }

}
