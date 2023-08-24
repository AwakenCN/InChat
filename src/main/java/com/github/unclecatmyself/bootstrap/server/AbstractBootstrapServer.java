package com.github.unclecatmyself.bootstrap.server;


import com.github.unclecatmyself.auto.ConfigManager;
import com.github.unclecatmyself.bootstrap.channel.AbstractHandlerService;
import com.github.unclecatmyself.bootstrap.handler.DefaultAbstractHandler;
import com.github.unclecatmyself.core.bean.InitNetty;
import com.github.unclecatmyself.core.constant.BootstrapConstant;
import com.github.unclecatmyself.core.constant.UndefinedInChatConstant;
import com.github.unclecatmyself.core.utils.ssl.SecureSocketSslContextFactory;
import com.github.unclecatmyself.core.utils.SslUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public abstract class AbstractBootstrapServer implements BootstrapServer {

    private static Logger logger = LoggerFactory.getLogger(AbstractBootstrapServer.class);

    private   String PROTOCOL = "TLS";

    private   SSLContext SERVER_CONTEXT;

    /**
     * @param channelPipeline  channelPipeline
     * @param serverBean  服务配置参数
     */
    protected  void initHandler(ChannelPipeline channelPipeline, InitNetty serverBean){
        if (serverBean.isSsl()){
            if (!ObjectUtils.allNotNull(serverBean.getJksCertificatePassword(),serverBean.getJksFile(),serverBean.getJksStorePassword())){
                throw new NullPointerException(UndefinedInChatConstant.SSL_NOT_FIND);
            }
            try {
                SSLContext context = SslUtil.createSSLContext("JKS",serverBean.getJksFile(),serverBean.getJksStorePassword());
                SSLEngine engine = context.createSSLEngine();
                engine.setUseClientMode(false);
                engine.setNeedClientAuth(false);
                channelPipeline.addLast(BootstrapConstant.SSL,new SslHandler(engine));
                System.out.println("open ssl  success");
            } catch (Exception e) {
                logger.error("AbstractBootstrapServer initHandler open ssl error, {}", e.getMessage(), e);
            }
        }
        initProtocolHandler(channelPipeline,serverBean);
        channelPipeline.addLast(new IdleStateHandler(serverBean.getHeart(),0,0));
        channelPipeline.addLast(new DefaultAbstractHandler(new AbstractHandlerService(ConfigManager.inChatVerifyService, ConfigManager.asyncListener)));
    }

    private  void initProtocolHandler(ChannelPipeline channelPipeline, InitNetty serverBean){
        channelPipeline.addLast(BootstrapConstant.HTTP_CODE,new HttpServerCodec());
//        channelPipeline.addLast("http-decoder",new HttpRequestDecoder());
        channelPipeline.addLast(BootstrapConstant.AGGREGATOR, new HttpObjectAggregator(serverBean.getMaxContext()));
//        channelPipeline.addLast("http-encoder",new HttpResponseEncoder());
        channelPipeline.addLast(BootstrapConstant.CHUNKED_WRITE,new ChunkedWriteHandler());
        channelPipeline.addLast(BootstrapConstant.WEB_SOCKET_HANDLER,new WebSocketServerProtocolHandler(serverBean.getWebSocketPath()));
    }

    private void initSsl(InitNetty serverBean){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {});
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        SSLContext serverContext;
        try {
            //
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load( SecureSocketSslContextFactory.class.getResourceAsStream(serverBean.getJksFile()),
                    serverBean.getJksStorePassword().toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks,serverBean.getJksCertificatePassword().toCharArray());
            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (Exception e) {
            throw new Error(
                    "Failed to initialize the server-side SSLContext", e);
        }
        SERVER_CONTEXT = serverContext;
    }

}
