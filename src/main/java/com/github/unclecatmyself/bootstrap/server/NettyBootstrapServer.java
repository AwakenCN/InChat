package com.github.unclecatmyself.bootstrap.server;

import com.github.unclecatmyself.core.bean.InitNetty;
import com.github.unclecatmyself.core.config.AutoConfig;
import com.github.unclecatmyself.core.config.RedisConfig;
import com.github.unclecatmyself.core.utils.PlatformUtil;
import com.github.unclecatmyself.core.utils.RemotingUtil;
import com.github.unclecatmyself.core.utils.UniqueIpUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
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

    Channel serverChannel = null;
    Class<?> serverSocketChannel = null;

    final Object waitLock = new Object(); //加锁，防止重复启动

    private final AtomicBoolean isStart = new AtomicBoolean(false);

    /**
     * 服务开启
     */
    public void start(int port) throws Exception {
        if (isStart.compareAndSet(false, true)) {
            try {
                if (port < 1) {
                    throw new RuntimeException(String.format("service port not set %s", port));
                }
                bootstrap = new ServerBootstrap();
                // 初始化EventPool
                boolean isUseEpoll = PlatformUtil.useEpoll();
                int ioModel = IoStrategy.nio;
                if (isUseEpoll) {
                    if (PlatformUtil.isLinuxPlatform()) {
                        ioModel = IoStrategy.epoll;
                    } else {
                        ioModel = IoStrategy.kqueue;
                    }
                }
                new InChatIoStrategy().chooseModel(ioModel);

                bootstrap.group(bossGroup, workGroup)
                        .channel((Class<? extends ServerChannel>) serverSocketChannel)
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
                ChannelFuture f = bootstrap.bind(port);
                serverChannel = f.sync().channel();
                f.addListener((ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        log.info("服务端启动成功【" + UniqueIpUtils.getHost() + ":" + port + "】");
                        AutoConfig.address = UniqueIpUtils.getHost() + ":" + port;
                        RedisConfig.getInstance();
                    } else {
                        log.info("服务端启动失败【" + UniqueIpUtils.getHost() + ":" + port + "】");
                    }
                });

            } catch (Throwable e) {
                log.error("rpc service start", e);
                isStart.set(false);
                throw e;
            }
        }
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        if(isStart.compareAndSet(true,false)){
            if (workGroup != null && bossGroup != null) {
                ChannelFuture f = serverChannel.close();
                f.awaitUninterruptibly();
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }
    }

    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }

    class InChatIoStrategy implements IoStrategy {

        @Override
        public void chooseModel(int model) {
            switch (model) {
                case 0: {
                    serverSocketChannel = NioServerSocketChannel.class;
                    bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), buildThreadFactory("BOSS_"));
                    workGroup = new NioEventLoopGroup(serverBean.getWorkerThread(), buildThreadFactory("WORK_"));
                    break;
                }
                case 1: {
                    serverSocketChannel = EpollServerSocketChannel.class;
                    bossGroup = new EpollEventLoopGroup(serverBean.getBossThread(), buildThreadFactory("LINUX_BOSS_"));
                    workGroup = new EpollEventLoopGroup(serverBean.getWorkerThread(), buildThreadFactory("LINUX_WORK_"));
                    break;
                }
                case 2: {
                    serverSocketChannel = KQueueServerSocketChannel.class;
                    bossGroup = new KQueueEventLoopGroup(serverBean.getBossThread(), buildThreadFactory("KQUEUE_BOSS_"));
                    workGroup = new KQueueEventLoopGroup(serverBean.getWorkerThread(), buildThreadFactory("KQUEUE_WORK_"));
                    break;
                }
                default:
                    break;
            }
        }
    }

    public static ThreadFactory buildThreadFactory(String threadName) {
        return new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, threadName + index.incrementAndGet());
            }
        };
    }

}
