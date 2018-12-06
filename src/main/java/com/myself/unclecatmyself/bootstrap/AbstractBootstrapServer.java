package com.myself.unclecatmyself.bootstrap;


import com.myself.unclecatmyself.common.properties.InitNetty;
import com.myself.unclecatmyself.common.utils.SpringBeanUtils;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public abstract class AbstractBootstrapServer implements BootstrapServer {

    /**
     * @param channelPipeline  channelPipeline
     * @param serverBean  服务配置参数
     */
    protected  void initHandler(ChannelPipeline channelPipeline, InitNetty serverBean){
        intProtocolHandler(channelPipeline,serverBean);
        channelPipeline.addLast(new IdleStateHandler(serverBean.getHeart(),0,0));
        channelPipeline.addLast(SpringBeanUtils.getBean(serverBean.getWebSocketHandler()));
    }

    private  void intProtocolHandler(ChannelPipeline channelPipeline,InitNetty serverBean){
        channelPipeline.addLast("httpCode",new HttpServerCodec());
        channelPipeline.addLast("aggregator", new HttpObjectAggregator(serverBean.getMaxContext()));
        channelPipeline.addLast("chunkedWrite",new ChunkedWriteHandler());
        channelPipeline.addLast("webSocketHandler",new WebSocketServerProtocolHandler(serverBean.getWebSocketPath()));
    }

}
