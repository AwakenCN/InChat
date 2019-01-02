package com.github.unclecatmyself;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.auto.InitServer;
import com.github.unclecatmyself.bootstrap.channel.ws.WebSocketChannelService;
import com.github.unclecatmyself.user.FromServerServiceImpl;
import com.github.unclecatmyself.user.MyInit;
import com.github.unclecatmyself.user.ToDataBaseServiceImpl;
import com.github.unclecatmyself.user.verifyServiceImpl;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DemoApplication {

	public static void main(String[] args) {
		//配置InChat配置工厂
		ConfigFactory.inChatToDataBaseService = new ToDataBaseServiceImpl();
		ConfigFactory.inChatVerifyService = new verifyServiceImpl();
		ConfigFactory.initNetty = new MyInit();
		ConfigFactory.fromServerService = new FromServerServiceImpl();
		//默认启动InChat
//		InitServer initServer = InitServer(new MyInit());
		InitServer.open();

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

