package com.myself.nettychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling //定时任务支持
@EnableAspectJAutoProxy //注解开启对aspectJ的支持
@EnableSwagger2 //Swagger自动生成文档
public class NettychatApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(NettychatApplication.class, args);
	}

}
