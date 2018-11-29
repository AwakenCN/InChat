package com.myself.nettychat.bootstrap.channel;

import com.myself.nettychat.bootstrap.WsChannelService;
import com.myself.nettychat.bootstrap.channel.cache.WsCacheMap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by MySelf on 2018/11/26.
 */
@Slf4j
@Component
public class WebSocketChannelService implements WsChannelService {

    @Autowired
    WsCacheMap wsCacheMap;

    @Override
    public void loginWsSuccess(Channel channel, String token) {
        wsCacheMap.saveWs(token,channel);
        wsCacheMap.saveAd(channel.remoteAddress().toString(),token);
    }

    @Override
    public boolean hasOther(String otherOne) {
        return wsCacheMap.hasToken(otherOne);
    }

    @Override
    public Channel getChannel(String otherOne) {
        return wsCacheMap.getByToken(otherOne);
    }

    @Override
    public void close(Channel channel) {
        log.info("【退出统一移除】");
        String token = wsCacheMap.getByAddress(channel.remoteAddress().toString());
        wsCacheMap.deleteAd(channel.remoteAddress().toString());
        wsCacheMap.deleteWs(token);
        channel.close();
    }


}
