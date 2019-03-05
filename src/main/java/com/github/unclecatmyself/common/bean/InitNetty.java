package com.github.unclecatmyself.common.bean;

import com.github.unclecatmyself.bootstrap.handler.DefaultHandler;

/**
 * 初始化Netty配置
 * Create by UncleCatMySelf in 2018/12/06
 */
public abstract class InitNetty {

    /** 通信地址 */
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

    /** 是否启动分布式 */
    private Boolean isDistributed = false;

    /** 是否启动加密 */
    private boolean ssl = false;

    private String jksFile = "inchat.jks";

    private String jksStorePassword = "123456";

    private String jksCertificatePassword = "123456";

    private Class<DefaultHandler> webSocketHandler = DefaultHandler.class;

    public Boolean getDistributed() {
        return isDistributed;
    }

    public void setDistributed(Boolean distributed) {
        isDistributed = distributed;
    }

    /**
     * 返回WebSocket启动监听端口
     * @return {@link Integer} WebSocket端口
     */
    public int getWebport() {
        return webport;
    }

    /**
     * 返回Netty核心线程个数
     * @return {@link Integer} Netty线程个数
     */
    public int getBossThread() {
        return bossThread;
    }

    public void setBossThread(int bossThread) {
        this.bossThread = bossThread;
    }

    /**
     * 返回Netty工作线程个数
     * @return {@link Integer} Netty工作线程个数
     */
    public int getWorkerThread() {
        return workerThread;
    }

    public void setWorkerThread(int workerThread) {
        this.workerThread = workerThread;
    }

    /**
     * 是否保持链接
     * @return {@link Boolean} 是否保持链接
     */
    public boolean isKeepalive() {
        return keepalive;
    }

    /**
     * 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
     * @return {@link Integer} 服务端接受连接的队列长度
     */
    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    /**
     * TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。
     * 该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，
     * 如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。
     * @return {@link Boolean} Nagle算法是否启用
     */
    public boolean isNodelay() {
        return nodelay;
    }

    /**
     * 地址复用，默认值False。有四种情况可以使用：
     * (1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。
     * (2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。
     * (3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。
     * (4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。
     * @return {@link Boolean} 地址复用
     */
    public boolean isReuseaddr() {
        return reuseaddr;
    }


    /**
     * TCP数据接收缓冲区大小。
     * @return {@link Integer} TCP数据接收缓冲区大小。
     */
    public int getRevbuf() {
        return revbuf;
    }

    public void setRevbuf(int revbuf) {
        this.revbuf = revbuf;
    }

    /**
     * 读超时时间
     * @return {@link Integer} 读超时时间
     */
    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * 消息 重发周期
     * @return {@link Integer} 消息重发周期
     */
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * 服务名称
     * @return {@link String} 服务名称
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 消息 重发延迟
     * @return {@link Integer} 消息重发延迟
     */
    public int getInitalDelay() {
        return initalDelay;
    }

    public void setInitalDelay(int initalDelay) {
        this.initalDelay = initalDelay;
    }

    public int getMaxContext() {
        return maxContext;
    }

    /**
     * WebSocket URL 后缀地址
     * @return {@link String} WebSocket URL 后缀地址
     */
    public String getWebSocketPath() {
        return webSocketPath;
    }


    public Class<DefaultHandler> getWebSocketHandler() {
        return webSocketHandler;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getJksFile() {
        return jksFile;
    }

    public void setJksFile(String jksFile) {
        this.jksFile = jksFile;
    }

    public String getJksStorePassword() {
        return jksStorePassword;
    }

    public void setJksStorePassword(String jksStorePassword) {
        this.jksStorePassword = jksStorePassword;
    }

    public String getJksCertificatePassword() {
        return jksCertificatePassword;
    }

    public void setJksCertificatePassword(String jksCertificatePassword) {
        this.jksCertificatePassword = jksCertificatePassword;
    }
}
