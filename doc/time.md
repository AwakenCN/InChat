## Netty自动重连机制

> 版本：netty 4.1.*
>
> 申明：本文旨在重新分享讨论Netty官方相关案例，添加部分个人理解与要点解析。

这个是InChat的案例[地址](https://github.com/AwakenCN/InChat/tree/official-demo/src/main/java/com/github/unclecatmyself/demo1)，里面补充了详细的注释，比起官方会容易看一点。

官方案例地址：https://netty.io/4.1/xref/io/netty/example/uptime/package-summary.html

### 正文

- UptimeClient（客户端）
- UptimeClientHandler
- UptimeServer（服务端）
- UptimeServerHandler

### 要点介绍

- IdleStateHandler  https://netty.io/4.1/api/io/netty/handler/timeout/IdleStateHandler.html

一个对Channel尚未执行读、写或两次操作的触发器

属性|含义
---|---
readerIdleTime | 在IdleStateEvent其状态IdleState.READER_IDLE 时的指定时间段没有执行读操作将被触发。指定0禁用。
writerIdleTime | 在IdleStateEvent其状态IdleState.WRITER_IDLE 时的指定时间段没有执行写操作将被触发。指定0禁用。
allIdleTime | 一个IdleStateEvent其状态IdleState.ALL_IDLE 时的时间在规定的时间进行读取和写入都将被触发。指定0禁用。

如下一个在没有信息时发送ping消息，且30秒没有入站信息则关闭连接

```java
public class MyChannelInitializer extends ChannelInitializer<Channel> {
      @Override
     public void initChannel(Channel channel) {
         channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 30, 0));
         channel.pipeline().addLast("myHandler", new MyHandler());
     }
 }

 // Handler should handle the IdleStateEvent triggered by IdleStateHandler.
 public class MyHandler extends ChannelDuplexHandler {
      @Override
     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         if (evt instanceof IdleStateEvent) {
             IdleStateEvent e = (IdleStateEvent) evt;
             if (e.state() == IdleState.READER_IDLE) {
                 ctx.close();
             } else if (e.state() == IdleState.WRITER_IDLE) {
                 ctx.writeAndFlush(new PingMessage());
             }
         }
     }
 }
```

### 项目源码

- UptimeClient

```java
/**
 * Created by MySelf on 2019/8/27.
 */
public final class UptimeClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    // 重新连接前睡眠5秒
    static final int RECONNECT_DELAY = Integer.parseInt(System.getProperty("reconnectDelay", "5"));
    // 当服务器在 10 秒内不发送任何内容时重新连接。
    private static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout", "10"));

    private static final UptimeClientHandler handler = new UptimeClientHandler();
    private static final Bootstrap bs = new Bootstrap();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        bs.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(HOST,PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(READ_TIMEOUT,0,0),handler);
                    }
                });
        bs.connect();
    }

    static void connect(){
        bs.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.cause() != null){
                    handler.startTime = -1;
                    handler.println("Failed to connect:" + future.cause());
                }
            }
        });
    }

}
```

- UptimeClientHandler

```java
/**
 * Created by MySelf on 2019/8/27.
 */
@ChannelHandler.Sharable
public class UptimeClientHandler extends SimpleChannelInboundHandler<Object> {

    long startTime = -1;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Discard received data
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (startTime < 0){
            startTime = System.currentTimeMillis();
        }
        println("Connected to:" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        println("Disconnected from: " + ctx.channel().remoteAddress());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)){
            return;
        }
        IdleStateEvent e = (IdleStateEvent)evt;
        if (e.state() == IdleState.READER_IDLE){
            // 连接正常，但是没有读信息，关闭连接
            println("Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // 睡眠5秒
        println("Sleeping for:" + UptimeClient.RECONNECT_DELAY + 's');
        // 启动线程重新连接
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                println("Reconnecting to:" + UptimeClient.HOST + ":" + UptimeClient.PORT);
                UptimeClient.connect();
            }
        },UptimeClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    void println(String msg){
        if (startTime < 0){
            System.err.format("[SERVER IS DOWN] %s%n",msg);
        } else {
            System.err.format("[UPTIME: %5ds] %s%n",(System.currentTimeMillis() - startTime)/1000,msg);
        }
    }
}
```

- UptimeServer

```java
/**
 * Created by MySelf on 2019/8/27.
 */
public final class UptimeServer {

    private static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    private static final UptimeServerHandler handler = new UptimeServerHandler();

    private UptimeServer(){}

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
```

- UptimeServerHandler

```java
/**
 * Created by MySelf on 2019/8/27.
 */
@ChannelHandler.Sharable
public class UptimeServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // discard
    }
}
```