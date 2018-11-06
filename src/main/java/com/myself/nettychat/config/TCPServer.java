package com.myself.nettychat.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:00 2018\8\14 0014
 */
@Data
@Component
public class TCPServer {

    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpServerBootstrap")
    private ServerBootstrap tcpServerBootstrap;


    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress tcpTcpPort;


    private Channel tcpServerChannel;


    public void startTcp() throws Exception {
        tcpServerChannel = tcpServerBootstrap.bind(tcpTcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() throws Exception {
        tcpServerChannel.close();
        tcpServerChannel.parent().close();
    }
}
