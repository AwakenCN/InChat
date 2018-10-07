package com.myself.nettychat.constont;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    /**存放链接池实例*/
    private Map<Object,Object> ChannelRedisMap = new ConcurrentHashMap<>();

    public void save(Object id,Object name){
        RedisMap.put(id,name);
        SecondRedisMap.put(name,id);
    }

    /**
     * 存储对应的用户名与Netty链接实例
     * @param name 登录用户名
     * @param channel Netty链接实例
     */
    public void saveChannel(Object name,Object channel){
        ChannelRedisMap.put(name,channel);
    }

    /**
     * 删除存储池实例
     * @param name 登录用户名
     */
    public void deleteChannel(Object name){
        ChannelRedisMap.remove(name);
    }

    /**
     * 获取存储池中的链接实例
     * @param name 登录用户名
     * @return {@link io.netty.channel.Channel 链接实例}
     */
    public Object getChannel(Object name){
        return ChannelRedisMap.get(name);
    }

    /**
     * 获取储存池链接数
     * @return 在线数
     */
    public Integer getSize(){
        return ChannelRedisMap.size();
    }

    /**
     * 获取连接对应用户名称
     * @param id 连接Id
     * @return 用户名称
     */
    public Object getName(Object id){
        return RedisMap.get(id);
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

    /**
     * 返回在线用户列表信息
     * @return 用户名列表
     */
    public Object getOnline() {
        List<Object> result = new ArrayList<>();
        for (Object key:ChannelRedisMap.keySet()){
            result.add(key);
        }
        return result;
    }
}
