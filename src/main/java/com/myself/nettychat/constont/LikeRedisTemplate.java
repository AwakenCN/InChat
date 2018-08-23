package com.myself.nettychat.constont;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:46 2018\8\14 0014
 */
@Component
public class LikeRedisTemplate {

    private Map<Object,Object> RedisMap = new ConcurrentHashMap<>();

    private Map<Object,Object> SecondRedisMap = new ConcurrentHashMap<>();

    public void save(Object id,Object name){
        RedisMap.put(id,name);
        SecondRedisMap.put(name,id);
    }

    public boolean check(Object id,Object name){
        if (SecondRedisMap.get(name) == null){
            return true;
        }
        if (id.equals(SecondRedisMap.get(name))){
            return true;
        }else{
            return false;
        }
    }

    public void delete(Object id){
        try {
            SecondRedisMap.remove(RedisMap.get(id));
            RedisMap.remove(id);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
