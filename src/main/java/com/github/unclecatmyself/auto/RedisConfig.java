package com.github.unclecatmyself.auto;

import com.github.unclecatmyself.common.constant.LogConstant;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Create by UncleCatMySelf in 13:56 2019\1\5 0005
 */
public class RedisConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    private static RedisConfig instance = new RedisConfig();

    public static Jedis jedis;

    private RedisConfig(){
        if (ConfigFactory.initNetty.getDistributed()){
            this.jedis = new Jedis(ConfigFactory.RedisIP);
            log.info(LogConstant.REDIS_START + jedis.ping());
        }
    }


    public static RedisConfig getInstance(){
        return instance;
    }

//    public static Jedis getJedis(){
//        Jedis jedis = null;
//        if (ConfigFactory.initNetty.getDistributed()){
//            jedis = new Jedis(ConfigFactory.RedisIP);
//            log.info(LogConstant.REDIS_START + jedis.ping());
//        }
//        return jedis;
//    }

}
