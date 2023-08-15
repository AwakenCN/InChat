package com.github.unclecatmyself.bootstrap.server;

/**
 * @author noseparte
 * @implSpec 通信策略
 * @since 2023/8/15 - 16:40
 * @version 1.0
 */
public interface IoStrategy {

    int nio = 0;
    int epoll = 1;
    int kqueue = 2;

    /**
     * 初始化EventPool 参数
     * @param model io模式
     */
    void chooseModel(int model);
}
