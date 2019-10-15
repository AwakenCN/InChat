package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigManager;
import com.github.unclecatmyself.auto.InChatServer;
import com.github.unclecatmyself.service.FromServerServiceImpl;
import com.github.unclecatmyself.service.InChatInitializer;
import com.github.unclecatmyself.service.UserAsyncDataListener;
import com.github.unclecatmyself.service.VerifyServiceImpl;

/**
 * Created by MySelf on 2019/8/26.
 */
public class Application {


    public static void main(String[] args) {
        ConfigManager.initNetty = new InChatInitializer();
        ConfigManager.fromServerService = FromServerServiceImpl.TYPE2;
        ConfigManager.asyncDataListener = new UserAsyncDataListener();
        ConfigManager.inChatVerifyService = new VerifyServiceImpl();
        InChatServer.open();
    }

}
