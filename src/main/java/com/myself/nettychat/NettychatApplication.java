package com.myself.nettychat;

import com.myself.nettychat.config.NettyConfig;
import com.myself.nettychat.config.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NettychatApplication {

	public static void main(String[] args) throws Exception{
//		SpringApplication.run(NettychatApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(NettychatApplication.class, args);
		NettyConfig nettyConfig = context.getBean(NettyConfig.class);
		TCPServer tcpServer = context.getBean(TCPServer.class);
		tcpServer.start();
	}

}
