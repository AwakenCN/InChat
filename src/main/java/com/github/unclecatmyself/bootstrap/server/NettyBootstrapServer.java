package com.github.unclecatmyself.bootstrap.server;

import com.github.unclecatmyself.core.config.AutoConfig;
import com.github.unclecatmyself.core.config.RedisConfig;
import com.github.unclecatmyself.core.bean.InitNetty;
import com.github.unclecatmyself.core.utils.UniqueIpUtils;
import com.github.unclecatmyself.core.utils.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class NettyBootstrapServer extends AbstractBootstrapServer {

    private final Logger log = LoggerFactory.getLogger(NettyBootstrapServer.class);

    private InitNetty serverBean;

    public void setServerBean(InitNetty serverBean) {
        this.serverBean = serverBean;
    }

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    ServerBootstrap bootstrap = null;// 启动辅助类

    Object waitLock = new Object(); //加锁，防止重复启动

    /**
     * 服务开启
     */
    public void start() {
        synchronized (waitLock) {
            initEventPool();
            bootstrap.group(bossGroup, workGroup)
                    .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, serverBean.isReuseaddr())
                    .option(ChannelOption.SO_BACKLOG, serverBean.getBacklog())
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_RCVBUF, serverBean.getRevbuf())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) {
                            initHandler(ch.pipeline(), serverBean);
                        }
                    })
                    .childOption(ChannelOption.TCP_NODELAY, serverBean.isNodelay())
                    .childOption(ChannelOption.SO_KEEPALIVE, serverBean.isKeepAlive())
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.bind(UniqueIpUtils.getHost(), serverBean.getWebPort()).addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("服务端启动成功【" + UniqueIpUtils.getHost() + ":" + serverBean.getWebPort() + "】");
                    AutoConfig.address = UniqueIpUtils.getHost() + ":" + serverBean.getWebPort();
                    RedisConfig.getInstance();
                } else {
                    log.info("服务端启动失败【" + UniqueIpUtils.getHost() + ":" + serverBean.getWebPort() + "】");
                }
            });
        }
    }

    /**
     * 初始化EventPool 参数
     */
    private void initEventPool() {
        bootstrap = new ServerBootstrap();
        if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(serverBean.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        synchronized (waitLock) {
            if (workGroup != null && bossGroup != null) {
                try {
                    bossGroup.shutdownGracefully().sync();// 优雅关闭
                    workGroup.shutdownGracefully().sync();
                } catch (InterruptedException e) {
                    log.error("服务端关闭资源失败【" + UniqueIpUtils.getHost() + ":" + serverBean.getWebPort() + "】");
                }
            }
        }
    }

    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }

}
