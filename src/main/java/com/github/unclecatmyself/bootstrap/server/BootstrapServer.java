package com.github.unclecatmyself.bootstrap.server;

import com.github.unclecatmyself.core.bean.InitNetty;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public interface BootstrapServer {

    void shutdown();

    void setServerBean(InitNetty serverBean);

    void start();

}
