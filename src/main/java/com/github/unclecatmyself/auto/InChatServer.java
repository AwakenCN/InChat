package com.github.unclecatmyself.auto;


import com.github.unclecatmyself.bootstrap.server.BootstrapServer;
import com.github.unclecatmyself.bootstrap.server.NettyBootstrapServer;
import com.github.unclecatmyself.core.bean.InitNetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InChat项目启动服务
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class InChatServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(InChatServer.class);

    /**
     * 静态化处理，保证唯一，确保用户启动的是他自己指定的，不是框架的
     * 一个数据配置集合
     */
    private static InitNetty serverBean = ConfigManager.initNetty;

    /**
     * netty服务器启动切面
     */
    private static BootstrapServer bootstrapServer;

    /**
     * 主要还是这个{@link NettyBootstrapServer},实例化想要的netty配置服务
     */
    public static void open() {
        /**
         * 静态化处理，保证唯一，确保用户启动的是他自己指定的，不是框架的
         * 一个数据配置集合
         */
        if (serverBean != null) {
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            int port = serverBean.getWebPort();
            if(LOGGER.isInfoEnabled()){
                LOGGER.info("Server started, listening on " + port);
            }
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
