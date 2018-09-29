package com.myself.nettychat.auto;


import com.myself.nettychat.bootstrap.BootstrapServer;
import com.myself.nettychat.bootstrap.NettyBootstrapServer;
import com.myself.nettychat.common.properties.InitNetty;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 初始化服务
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
