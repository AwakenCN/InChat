package com.github.unclecatmyself.support;

import redis.clients.jedis.Jedis;

/**
 * Create by UncleCatMySelf in 23:02 2019\1\4 0004
 */
public class RedisClient {

    public static void main(String[] args) {
        //连接redis服务
        Jedis jedis = new Jedis("192.168.12.129");
        System.out.println("连接成功");
        //设置redis字符串数据
        //jedis.set("token","inchat");
        //查看服务是否运行
        System.out.println("服务正在运行："+jedis.ping());
        //获取存储的数据并输出
        System.out.println("redis存储的字符串为："+jedis.get("token"));
    }

}
