package com.myself.nettychat.constont;

import com.myself.nettychat.dataobject.UserMsg;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天内容临时存储
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:38 2018\8\14 0014
 */
@Component
public class LikeSomeCacheTemplate {

    private Set<UserMsg> SomeCache = new LinkedHashSet<>();

    public void save(Object user,Object msg){
        UserMsg userMsg = new UserMsg();
        userMsg.setName(String.valueOf(user));
        userMsg.setMsg(String.valueOf(msg));
        SomeCache.add(userMsg);
    }

    public Set<UserMsg> cloneCacheMap(){
        return SomeCache;
    }

    public void clearCacheMap(){
        SomeCache.clear();
    }
}
