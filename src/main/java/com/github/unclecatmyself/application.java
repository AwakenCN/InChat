package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.users.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create by UncleCatMySelf in 22:49 2019\1\4 0004
 */
@SpringBootApplication
public class application {

    public static void main(String[] args) {
        SpringApplication.run(application.class, args);
        ConfigFactory.initNetty = new MyInit();
        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
        ConfigFactory.inChatToDataBaseService = new DataBaseServiceImpl();
        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;
        ConfigFactory.listenAsynData = new UserTextData();
//        ConfigFactory.RedisIP = "192.168.0.101";
//        ConfigFactory.RedisIP = "192.168.192.132";

        InitServer.open();
    }

}
