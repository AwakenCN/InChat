package com.myself.unclecatmyself.bootstrap;

import com.myself.unclecatmyself.common.properties.InitNetty;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
