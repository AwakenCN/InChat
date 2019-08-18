## Echo简易通讯案例

> 版本：netty 4.1.*
>
> 申明：本文旨在重新分享讨论Netty官方相关案例，添加部分个人理解与要点解析。

这个是InChat的案例[地址](https://github.com/AwakenCN/InChat/tree/official-demo/src/main/java/com/github/unclecatmyself/demo1)，里面补充了详细的注释，比起官方会容易看一点。

官方案例地址：https://netty.io/4.1/xref/io/netty/example/echo/package-summary.html

### 正文

- EchoClient（客户端）
- EchoClientHandler
- EchoServer（服务端）
- EchoServerHandler

### 要点介绍

- SslContext  

官方介绍：https://netty.io/4.1/api/io/netty/handler/ssl/SslContext.html

公共抽象类，安全套接字协议实现充当工厂SSLEngine和SslHandler。在内部，它通过JDK SSLContext或OpenSSL 实现SSL_CTX，还有关于它的使用方式，如果你需要ssl加密的话

- SslContextBuilder

官方介绍：https://netty.io/4.1/api/io/netty/handler/ssl/SslContextBuilder.html

用于配置新SslContext以进行创建的构建器，其中包含多个方法这里就不多补充，大家可以去看看

- InsecureTrustManagerFactory

官方介绍：https://netty.io/4.1/api/io/netty/handler/ssl/util/InsecureTrustManagerFactory.html

在TrustManagerFactory没有任何验证的情况下信任所有X.509证书的不安全因素

> 注意：切勿TrustManagerFactory在生产中使用它。它纯粹是出于测试目的，因此非常不安全。

- SelfSignedCertificate

官方介绍：https://netty.io/4.1/api/io/netty/handler/ssl/util/SelfSignedCertificate.html

生成临时自签名证书以进行测试

> 注意：切勿在生产中使用此类生成的证书和私钥。它纯粹是出于测试目的，因此非常不安全。它甚至使用不安全的伪随机生成器在内部更快地生成

### 项目源码

- EchoClient

```java
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
```

- EchoClientHandler

```java
/**
 * @ClassName EchoClientHandler
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/17 18:06
 * @Version 1.0
 **/
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    public EchoClientHandler(){
        //获取EchoClient的SIZE
        //Unpooled:ByteBuf通过分配新空间或通过包装或复制现有字节数组，字节缓冲区和字符串来创建新的
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i++){
            firstMessage.writeByte((byte)i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

- EchoServer

```java
/**
 * @ClassName EchoServer
 * @Description 服务端
 * @Author MySelf
 * @Date 2019/8/17 18:15
 * @Version 1.0
 **/
public final class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        final SslContext sslCtx;
        if (SSL){
            //SelfSignedCertificate:生成临时自签名证书以进行测试
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        }else{
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoServerHandler serverHandler = new EchoServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null){
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind(PORT).sync();

            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
```

- EchoServerHandler

```java
/**
 * @ClassName EchoServerHandler
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/17 18:14
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```