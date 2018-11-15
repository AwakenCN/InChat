package com.myself.nettychat.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.myself.nettychat.common.enums.ProtocolEnum;
import com.myself.nettychat.common.mqtts.MqttHander;

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

    private  int  sndbuf;

    private int revbuf;

    private int heart;

    private int period;

    private String serverName;

    private int initalDelay;

}
