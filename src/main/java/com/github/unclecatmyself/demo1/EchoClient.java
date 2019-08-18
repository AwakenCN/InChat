package com.github.unclecatmyself.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * @ClassName EchoClient
 * @Description 一个简单的应答通讯的实例
 * @Author MySelf
 * @Date 2019/8/17 17:56
 * @Version 1.0
 **/
public final class EchoClient {

    //判断是否加密
    static final boolean SSL = System.getProperty("ssl") != null;
    //监听本地服务
    static final String HOST = System.getProperty("host", "127.0.0.1");
    //监听端口
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    //发送消息的大小，用于EchoClientHandler
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        //公共抽象类，安全套接字协议实现充当工厂SSLEngine和SslHandler。在内部，它通过JDK SSLContext或OpenSSL 实现SSL_CTX
        final SslContext sslCtx;
        if (SSL){
            //用于配置新SslContext以进行创建的构建器
            sslCtx = SslContextBuilder.forClient()
                    //用于验证远程端点证书的可信管理器
                    //InsecureTrustManagerFactory:在TrustManagerFactory没有任何验证的情况下信任所有X.509证书的不安全因素
                    //注：切勿TrustManagerFactory在生产中使用它。它纯粹是出于测试目的，因此非常不安全。
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }else {
            sslCtx = null;
        }
        //事件循环
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline p = sc.pipeline();
                            //了解SslContext的用法
                            if (sslCtx != null){
                                p.addLast(sslCtx.newHandler(sc.alloc(),HOST,PORT));
                            }
                            p.addLast(new EchoClientHandler());
                        }
                    });
            //这个sync后的代码均会执行
            ChannelFuture f = b.connect(HOST,PORT).sync();
            System.out.println("before-----");

            //这个sync后的代码不会执行
            f.channel().closeFuture().sync();
            System.out.println("after-----");
        }finally {
            group.shutdownGracefully();
        }
    }

}
