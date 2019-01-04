package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.common.bean.InitNetty;
import com.github.unclecatmyself.user.DataBaseServiceImpl;
import com.github.unclecatmyself.user.FromServerServiceImpl;
import com.github.unclecatmyself.user.VerifyServiceImpl;

/**
 * Create by UncleCatMySelf in 22:49 2019\1\4 0004
 */
public class application {

    public static void main(String[] args) {
        ConfigFactory.initNetty = new InitNetty();
        ConfigFactory.inChatVerifyService = new VerifyServiceImpl();
        ConfigFactory.inChatToDataBaseService = new DataBaseServiceImpl();
        ConfigFactory.fromServerService = FromServerServiceImpl.TYPE2;

        InitServer.open();
    }

}
