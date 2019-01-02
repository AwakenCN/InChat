package com.github.unclecatmyself.bootstrap.channel.http;

import com.github.unclecatmyself.auto.ConfigFactory;
import com.github.unclecatmyself.bootstrap.channel.cache.WsCacheMap;
import com.github.unclecatmyself.common.bean.vo.*;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Create by UncleCatMySelf in 11:41 2018\12\31 0031
 */
public class HttpChannelServiceImpl implements HttpChannelService {

    private static final Logger log = LoggerFactory.getLogger(HttpChannelServiceImpl.class);

    private static FromServerService fromServerService = ConfigFactory.fromServerService;

    @Override
    public void getSize(Channel channel) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set("Content-Type","text/html;charset=UTF-8");
        GetSizeVO getSizeVO = new GetSizeVO(WsCacheMap.getSize(),new Date());
        ResultVO<GetSizeVO> resultVO = new ResultVO<>(HttpResponseStatus.OK.code(),getSizeVO);
        Gson gson = new Gson();
        ByteBuf buf = Unpooled.copiedBuffer(gson.toJson(resultVO), CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        channel.writeAndFlush(response);
        close(channel);
    }

    @Override
    public void sendFromServer(Channel channel, SendServerVO serverVO) {
        if (serverVO.getToken() == ""){
            notFindUri(channel);
        }
        try {
            Channel userChannel = WsCacheMap.getByToken(serverVO.getToken());
            String value = fromServerService.findByCode(serverVO.getValue());
        }catch (NullPointerException e){
            log.info("[HttpChannelServiceImpl.sendFromServer] 未找到用户在线标识");
            notFindToken(channel);
        }

    }

    private void sendServer(Channel channel, SendServerVO serverVO){

    }

    private void notFindToken(Channel channel) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        response.headers().set("Content-Type","text/html;charset=UTF-8");
        NotFindUriVO notFindUriVO = new NotFindUriVO("未找到在线用户标识");
        ResultVO<NotFindUriVO> resultVO = new ResultVO<>(HttpResponseStatus.BAD_REQUEST.code(),notFindUriVO);
        Gson gson = new Gson();
        ByteBuf buf = Unpooled.copiedBuffer(gson.toJson(resultVO), CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        channel.writeAndFlush(response);
        close(channel);
    }

    @Override
    public void notFindUri(Channel channel) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        response.headers().set("Content-Type","text/html;charset=UTF-8");
        NotFindUriVO notFindUriVO = new NotFindUriVO("未找到匹配任务URI");
        ResultVO<NotFindUriVO> resultVO = new ResultVO<>(HttpResponseStatus.BAD_REQUEST.code(),notFindUriVO);
        Gson gson = new Gson();
        ByteBuf buf = Unpooled.copiedBuffer(gson.toJson(resultVO), CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        channel.writeAndFlush(response);
        close(channel);
    }

    @Override
    public void close(Channel channel) {
        log.info("[HttpChannelServiceImpl.close] 关闭HTTP通道连接");
        channel.close();
    }

    @Override
    public void getList(Channel channel) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set("Content-Type","text/html;charset=UTF-8");
        GetListVO getListVO = new GetListVO(WsCacheMap.getTokenList());
        ResultVO<GetListVO> resultVO = new ResultVO<>(HttpResponseStatus.OK.code(),getListVO);
        Gson gson = new Gson();
        ByteBuf buf = Unpooled.copiedBuffer(gson.toJson(resultVO), CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        channel.writeAndFlush(response);
        close(channel);
    }
}
