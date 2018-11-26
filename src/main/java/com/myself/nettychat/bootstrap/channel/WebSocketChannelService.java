package com.myself.nettychat.bootstrap.channel;

import com.myself.nettychat.bootstrap.WsChannelService;
import com.myself.nettychat.bootstrap.channel.cache.WsCacheMap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    }


}
