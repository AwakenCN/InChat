package com.github.unclecatmyself.common.properties;

import com.github.unclecatmyself.bootstrap.handler.DefaultWebSocketHandler;
import lombok.Data;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
public class InitNetty {

    private int webport = 8090;

    private int bossThread = 1;

    private int workerThread = 2;

    private boolean keepalive = true;

    private int backlog = 1024;

    private boolean nodelay = true;

    private boolean reuseaddr = true;

    private  int  sndbuf = 10485760;

    private int revbuf = 10485760;

    private int heart = 180;

    private int period = 10;

    private String serverName = "iot-netty-chat";

    private int initalDelay = 10;

    private int maxContext = 65536;

    private String webSocketPath = "/ws";

    private Class<DefaultWebSocketHandler> webSocketHandler = DefaultWebSocketHandler.class;

    public int getWebport() {
        return webport;
    }

    public void setWebport(int webport) {
        this.webport = webport;
    }

    public int getBossThread() {
        return bossThread;
    }

    public void setBossThread(int bossThread) {
        this.bossThread = bossThread;
    }

    public int getWorkerThread() {
        return workerThread;
    }

    public void setWorkerThread(int workerThread) {
        this.workerThread = workerThread;
    }

    public boolean isKeepalive() {
        return keepalive;
    }

    public void setKeepalive(boolean keepalive) {
        this.keepalive = keepalive;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public boolean isNodelay() {
        return nodelay;
    }

    public void setNodelay(boolean nodelay) {
        this.nodelay = nodelay;
    }

    public boolean isReuseaddr() {
        return reuseaddr;
    }

    public void setReuseaddr(boolean reuseaddr) {
        this.reuseaddr = reuseaddr;
    }

    public int getSndbuf() {
        return sndbuf;
    }

    public void setSndbuf(int sndbuf) {
        this.sndbuf = sndbuf;
    }

    public int getRevbuf() {
        return revbuf;
    }

    public void setRevbuf(int revbuf) {
        this.revbuf = revbuf;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getInitalDelay() {
        return initalDelay;
    }

    public void setInitalDelay(int initalDelay) {
        this.initalDelay = initalDelay;
    }

    public int getMaxContext() {
        return maxContext;
    }

    public void setMaxContext(int maxContext) {
        this.maxContext = maxContext;
    }

    public String getWebSocketPath() {
        return webSocketPath;
    }

    public void setWebSocketPath(String webSocketPath) {
        this.webSocketPath = webSocketPath;
    }

    public Class<DefaultWebSocketHandler> getWebSocketHandler() {
        return webSocketHandler;
    }

    public void setWebSocketHandler(Class<DefaultWebSocketHandler> webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }
}
