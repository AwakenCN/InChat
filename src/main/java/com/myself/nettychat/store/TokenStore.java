package com.myself.nettychat.store;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局Token存储环境实例
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 18:00 2018\8\13 0013
 */
@Data
public class TokenStore {

    private static Map<String,Object> TokenStoreMap = new ConcurrentHashMap<String,Object>();

    /**
     * 添加到全局Token缓存中
     * @param token
     * @param userId
     */
    public static void add(String token,Object userId){
        TokenStoreMap.put(token,userId);
    }

    /**
     * 从全局Token缓存中移除
     * @param token
     */
    public static void remove(String token){
        TokenStoreMap.remove(token);
    }

    /**
     * 获取Token对象
     * @param token
     * @return
     */
    public static Object get(String token){
        return TokenStoreMap.get(token);
    }
}
