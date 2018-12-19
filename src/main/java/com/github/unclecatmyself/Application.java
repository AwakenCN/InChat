package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.common.properties.InitNetty;

/**
 * Created by MySelf on 2018/12/18.
 */
public class Application {

    public static void main(String[] args) {
        //注册InChat相关配置
        InitServer initServer = new InitServer(new InitNetty());
        initServer.open();
        //预制InChat的用户接口

        //启动InChat流程
        

    }

}
