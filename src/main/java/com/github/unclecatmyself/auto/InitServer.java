package com.github.unclecatmyself.auto;


import com.github.unclecatmyself.bootstrap.BootstrapServer;
import com.github.unclecatmyself.bootstrap.NettyBootstrapServer;
import com.github.unclecatmyself.common.bean.InitNetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InChat项目启动服务
 * Create by UncleCatMySelf in 2018/12/06
 **/
public abstract class InitServer {

    /**
     * 静态化处理，保证唯一，确保用户启动的是他自己指定的，不是框架的
     * 一个数据配置集合
     */
    private static InitNetty serverBean = ConfigFactory.initNetty;

    /**
     * netty服务器启动切面
     */
    static BootstrapServer bootstrapServer;

    /**
     * 主要还是这个{@link NettyBootstrapServer},实例化想要的netty配置服务
     */
    public static void open() {
        if (serverBean != null) {
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            int port = serverBean.getWebport();
            bootstrapServer.start();
        }
    }

    /**
     * 关闭服务
     */
    public void close() {
        if (bootstrapServer != null) {
            bootstrapServer.shutdown();
        }
    }

}
