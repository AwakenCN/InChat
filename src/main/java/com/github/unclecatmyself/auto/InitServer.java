package com.github.unclecatmyself.auto;


import com.github.unclecatmyself.bootstrap.BootstrapServer;
import com.github.unclecatmyself.bootstrap.NettyBootstrapServer;
import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class InitServer {

    private InitNetty serverBean;

    public InitServer(InitNetty serverBean) {
        this.serverBean = serverBean;
    }

    BootstrapServer bootstrapServer;

    public void open(){
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
