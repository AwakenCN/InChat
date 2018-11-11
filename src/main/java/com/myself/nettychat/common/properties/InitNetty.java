package com.myself.nettychat.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:54 2018\8\14 0014
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class InitNetty {

    private int webport;

    private int tcpport;

    private int mqttport;

    private int bossThread;

    private int workerThread;

    private boolean keepalive;

    private int backlog;

    private boolean nodelay;

    private boolean reuseaddr;

    private String serverName ;

    private  int  sndbuf ;

    private int revbuf ;

    private int heart ;

    private boolean ssl ;

    private String jksFile;

    private String jksStorePassword;

    private String jksCertificatePassword;

    private int  initalDelay ;

    private  int period ;

}
