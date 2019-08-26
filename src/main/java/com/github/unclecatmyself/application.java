package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.user.FromServerServiceImpl;
import com.github.unclecatmyself.user.MyInit;
import com.github.unclecatmyself.user.UserListenAsynData;
import com.github.unclecatmyself.user.VerifyServiceImpl;

/**
 * Created by MySelf on 2019/8/26.
 */
public class application {

    public static void main(String[] args) {
        ConfigFactory.initNetty = new MyInit();
        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;
        ConfigFactory.listenAsynData = new UserListenAsynData();
        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
        InitServer.open();
    }

}
