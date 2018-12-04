package com.myself.unclecatmyself.common.properties;

import com.myself.unclecatmyself.bootstrap.handler.DefaultWebSocketHandler;
import com.myself.unclecatmyself.common.websockets.WebSocketHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:54 2018\8\14 0014
 */
@Data
@ConfigurationProperties(prefix = "inchat")
public class InitNetty {

    private int webport = 8090;

    private int bossThread = 1;

    private int workerThread = 2;

    private boolean keepalive = true;

    private int backlog = 1024;

    private boolean nodelay = true;

    private boolean reuseaddr = true;

    private  int  sndbuf = 10485760;

    private int revbuf = 10485760;

    private int heart = 180;

    private int period = 10;

    private String serverName = "iot-netty-chat";

    private int initalDelay = 10;

    private int maxContext = 65536;

    private String webSocketPath = "/ws";

    private Class<DefaultWebSocketHandler> webSocketHandler = DefaultWebSocketHandler.class;

}
