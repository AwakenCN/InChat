## 无限异步发送数据流

> 版本：netty 4.1.*
>
> 申明：本文旨在重新分享讨论Netty官方相关案例，添加部分个人理解与要点解析。

这个是[InChat](https://github.com/AwakenCN/InChat)的案例[地址](https://github.com/AwakenCN/InChat/tree/official-demo/src/main/java/com/github/unclecatmyself/demo2)，里面补充了详细的注释，比起官方会容易看一点。

官方案例地址：https://netty.io/4.1/xref/io/netty/example/echo/package-summary.html

### 正文

- DiscardClient（客户端）
- DiscardClientHandler
- DiscardServer（服务端）
- DiscardServerHandler

### 要点介绍

- ChannelInboundHandlerAdapter [官方介绍](https://netty.io/4.1/api/io/netty/channel/ChannelInboundHandlerAdapter.html)

实现了抽象基类ChannelInboundHandler，因此也提供其所有方法的实现,子类可以重写方法实现来改变它。

> 注意：channelRead(ChannelHandlerContext, Object) 方法自动返回后不会释放消息。如果您正在寻找ChannelInboundHandler自动发布收到的消息的实现，请参阅SimpleChannelInboundHandler

- SimpleChannelInboundHandler [官方介绍](https://netty.io/4.1/api/io/netty/channel/SimpleChannelInboundHandler.html)

允许显式只处理特定类型的消息

- writeZero(int length) [官方介绍](https://netty.io/4.1/api/io/netty/buffer/ByteBuf.html#writeZero-int-)

从当前开始 用（0x00）填充此缓冲区writerIndex并按writerIndex指定值增加length。如果this.writableBytes小于length，ensureWritable(int) 将调用以尝试扩展容量以适应。

- directBuffer(int initialCapacity) [官方介绍](https://netty.io/4.1/api/io/netty/buffer/ByteBufAllocator.html#directBuffer-int-)

使用给定的初始值给ByteBuf分配直接值

- release() [官方介绍](https://netty.io/4.1/api/io/netty/util/ReferenceCounted.html#release--)

每一个新分配的ByteBuf的引用计数值为1，每对这个ByteBuf对象增加一个引用，需要调用ByteBuf.retain()方法，而每减少一个引用，需要调用ByteBuf.release()方法。当这个ByteBuf对象的引用计数值为0时，表示此对象可回收

- retainedDuplicate() [官方介绍](https://netty.io/4.1/api/io/netty/buffer/ByteBuf.html#retainedDuplicate--)

返回保留的缓冲区，该缓冲区共享此缓冲区的整个区域。修改返回的缓冲区或此缓冲区的内容会影响彼此的内容，同时它们会维护单独的索引和标记。此方法的行为类似于此方法，duplicate().retain()但此方法可能返回产生较少垃圾的缓冲区实现。

- ChannelFutureListener [官方介绍](https://netty.io/4.1/api/io/netty/channel/ChannelFutureListener.html)

监听一个ChannelFuture的结果。Channel一旦通过调用添加此侦听器，将以ChannelFuture.addListener(GenericFutureListener)异步I / O的操作通知结果。

### 项目源码


- DiscardClient（客户端）

```java
/**
 * @ClassName DiscardClient
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/19 20:38
 * @Version 1.0
 **/
public final class DiscardClient {

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
                            p.addLast(new DiscardClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(HOST,PORT).sync();

            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

}
```

- DiscardClientHandler

```java
/**
 * @ClassName DiscardClientHandler
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/19 20:38
 * @Version 1.0
 **/
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        // 初始化消息
        content = ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);

        // 发送初始信息
        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //引用计数为0，释放
        content.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 引发异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // 服务器应该不发送任何内容，但如果它发送什么，丢弃它。
    }

    // 生成数据
    private void generateTraffic(){
        // 将出站缓冲区刷新到套接字
        // 刷新后，再次生成相同数量的流量
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }

    // 数据触发
    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        //完成操作后的方法调用，即只要成功无限调用generateTraffic()
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (channelFuture.isSuccess()){
                generateTraffic();
            }else {
                channelFuture.cause().printStackTrace();
                channelFuture.channel().close();
            }
        }
    };

}
```

- DiscardServer（服务端）

```java
/**
 * @ClassName DiscardServer
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/19 20:38
 * @Version 1.0
 **/
public class DiscardServer {

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
                            p.addLast(new DiscardServerHandler());
                        }
                    });

            // 绑定并开始接受传入连接
            ChannelFuture f = b.bind(PORT).sync();

            // 等待服务器套接字关闭
            // 这个例子，这不会发生，但你可以这样优雅的做
            f.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
```

- DiscardServerHandler

```java
/**
 * @ClassName DiscardServerHandler
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/19 20:39
 * @Version 1.0
 **/
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // discard
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 引发异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
```