package com.myself.nettychat.constont;

import org.omg.CORBA.OBJ_ADAPTER;
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

    public void save(Object id,Object name){
        RedisMap.put(id,name);
    }

    public void delete(Object id){
        RedisMap.remove(id);
    }

    public Object get(Object id){
        return RedisMap.get(id);
    }
}
