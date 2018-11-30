package com.myself.unclecatmyself.bootstrap;

import com.myself.unclecatmyself.common.properties.InitNetty;

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
