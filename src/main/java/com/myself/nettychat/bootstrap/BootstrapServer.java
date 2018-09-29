package com.myself.nettychat.bootstrap;

import com.myself.nettychat.common.properties.InitNetty;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 启动类接口
 **/
public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
