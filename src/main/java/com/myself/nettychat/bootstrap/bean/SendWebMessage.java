package com.myself.nettychat.bootstrap.bean;

import com.myself.nettychat.common.enums.ConfirmStatus;
import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;

/**
 * WebSocket 消息
 * Created by MySelf on 2018/11/21.
 */
@Data
@Builder
public class SendWebMessage {

    private int messageId;

    private Channel channel;

    private volatile ConfirmStatus confirmStatus;

    private long time;

    private byte[] byteBuf;

    private boolean isRetain;

}
