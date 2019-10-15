package com.github.unclecatmyself.bootstrap.channel.ws;

import com.github.unclecatmyself.bootstrap.channel.protocol.SocketChannel;
import com.google.gson.Gson;
import com.github.unclecatmyself.bootstrap.channel.cache.WebSocketCacheMap;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

/**
 * Created by MySelf on 2018/11/26.
 */
public class WebSocketChannel implements SocketChannel {

    @Override
    public void loginWsSuccess(Channel channel, String token) {
        WebSocketCacheMap.saveWs(token,channel);
        WebSocketCacheMap.saveAd(channel.remoteAddress().toString(),token);
    }

    @Override
    public boolean hasOther(String otherOne) {
        return WebSocketCacheMap.hasToken(otherOne);
    }

    @Override
    public Channel getChannel(String otherOne) {
        return WebSocketCacheMap.getByToken(otherOne);
    }

    @Override
    public String close(Channel channel) {
        String token = WebSocketCacheMap.getByAddress(channel.remoteAddress().toString());
        WebSocketCacheMap.deleteAd(channel.remoteAddress().toString());
        WebSocketCacheMap.deleteWs(token);
        channel.close();
        return token;
    }

    @Override
    public boolean sendFromServer(Channel channel, Map<String, String> map) {
        Gson gson = new Gson();
        try {
            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(map)));
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
