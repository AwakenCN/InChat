package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.bootstrap.channel.WebSocketChannelService;
import com.github.unclecatmyself.common.bean.InitNetty;
import com.github.unclecatmyself.user.DataBaseServiceImpl;
import com.github.unclecatmyself.user.InChatVerifyServiceImpl;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by MySelf on 2018/12/18.
 */
public class Application {

    public static void main(String[] args) {
        //配置InChat配置工厂
        ConfigFactory.inChatToDataBaseService = new DataBaseServiceImpl();
        ConfigFactory.inChatVerifyService = new InChatVerifyServiceImpl();
        //默认启动InChat
        InitServer initServer = new InitServer(new InitNetty());
        initServer.open();

        //获取用户值
        WebSocketChannelService webSocketChannelService = new WebSocketChannelService();

        //启动新线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设定默认服务器发送值
                Map<String,String> map = new HashMap<>();
                map.put("server","服务器");
                //获取控制台用户想发送的用户Token
                Scanner scanner = new Scanner(System.in);
                String token = scanner.nextLine();
                //获取用户连接
                Channel channel = (Channel) webSocketChannelService.getChannel(token);
                //调用接口发送
                webSocketChannelService.sendFromServer(channel,map);
            }
        }).start();

    }

}
