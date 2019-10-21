package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigManager;
import com.github.unclecatmyself.auto.InChatServer;
import com.github.unclecatmyself.support.FromServerServiceImpl;
import com.github.unclecatmyself.support.InChatInitializer;
import com.github.unclecatmyself.support.UserAsyncDataListener;
import com.github.unclecatmyself.support.VerifyServiceImpl;

/**
 * Created by MySelf on 2019/8/26.
 */
public class Application {

    public static void main(String[] args) {
        ConfigManager.initNetty = new InChatInitializer();
        ConfigManager.fromServerService = FromServerServiceImpl.TYPE2;
        ConfigManager.asyncListener = new UserAsyncDataListener();
        ConfigManager.inChatVerifyService = new VerifyServiceImpl();
        InChatServer.open();
    }

}
