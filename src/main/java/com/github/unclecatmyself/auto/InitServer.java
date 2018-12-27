package com.github.unclecatmyself.auto;


import com.github.unclecatmyself.bootstrap.BootstrapServer;
import com.github.unclecatmyself.bootstrap.NettyBootstrapServer;
import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * InChat项目启动服务
 * Create by UncleCatMySelf in 2018/12/06
 **/
public abstract class InitServer {

//    private InitNetty serverBean;

    private static InitNetty serverBean = ConfigFactory.initNetty;

//    public InitServer(InitNetty serverBean) {
//        this.serverBean = serverBean;
//    }

    static BootstrapServer bootstrapServer;

    public static void open(){
        if(serverBean!=null){
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            bootstrapServer.start();
        }
    }

    public void close(){
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
        }
    }

}
