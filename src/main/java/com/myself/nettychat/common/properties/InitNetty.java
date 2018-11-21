package com.myself.nettychat.common.properties;

import com.myself.nettychat.common.websockets.WebSocketHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:54 2018\8\14 0014
 */
@Data
@ConfigurationProperties(prefix = "netty")
public class InitNetty {

    private int webport;

    private int bossThread;

    private int workerThread;

    private boolean keepalive;

    private int backlog;

    private boolean nodelay;

    private boolean reuseaddr;

    private  int  sndbuf;

    private int revbuf;

    private int heart;

    private int period;

    private String serverName;

    private int initalDelay;

    private int maxContext;

    private String webSocketPath;

    private Class<WebSocketHandler> webSocketHandler;

}
