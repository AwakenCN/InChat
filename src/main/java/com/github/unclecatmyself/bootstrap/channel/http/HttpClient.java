package com.github.unclecatmyself.bootstrap.channel.http;

import com.github.unclecatmyself.common.bean.SendInChat;
import com.github.unclecatmyself.common.constant.HttpConstant;
import com.github.unclecatmyself.common.utils.SslUtil;
import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.URI;
import java.util.Map;

/**
 * Create by UncleCatMySelf in 17:47 2019\1\5 0005
 */
public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private static HttpClient instance = new HttpClient();

    public static Bootstrap bootstrap;

    private HttpClient(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
            ch.pipeline().addLast(new HttpResponseDecoder());
            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
            ch.pipeline().addLast(new HttpRequestEncoder());
            try {
                SSLContext context = SslUtil.createSSLContext("JKS","inchat.jks","123456");
                SSLEngine engine = context.createSSLEngine();
                engine.setUseClientMode(true);
//                engine.setNeedClientAuth(false);
                ch.pipeline().addLast("ssl",new SslHandler(engine));
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        });
        this.bootstrap = b;
    }

    public static HttpClient getInstance(){
        return instance;
    }

    public void send(String host, int port,String token,Map value) throws Exception {
        // Start the client.
        ChannelFuture f = this.bootstrap.connect(host, port).sync();

        URI uri = new URI(HttpConstant.URI_SENDINCHAT);

        Gson gson = new Gson();
        String content = gson.toJson(new SendInChat(token,value));
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(),Unpooled.wrappedBuffer(content.getBytes(CharsetUtil.UTF_8)));

        // 构建http请求
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
//      request.headers().set(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);

        // 发送http请求
        f.channel().write(request);
        f.channel().flush();
        f.channel().closeFuture().sync();
    }

//    public static void main(String[] args) throws Exception {
//        HttpClient client = new HttpClient();
//        client.connect("192.168.1.121",8090);
//    }

}
