package com.github.unclecatmyself.common.bean;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Create by UncleCatMySelf in 16:06 2019\1\5 0005
 */
public class SendInChat {

    private String token;

    private TextWebSocketFrame frame;

    public SendInChat() {
    }

    public SendInChat(String token, TextWebSocketFrame frame) {
        this.token = token;
        this.frame = frame;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TextWebSocketFrame getFrame() {
        return frame;
    }

    public void setFrame(TextWebSocketFrame frame) {
        this.frame = frame;
    }
}
