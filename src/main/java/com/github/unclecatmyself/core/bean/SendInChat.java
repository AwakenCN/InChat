package com.github.unclecatmyself.core.bean;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 16:06 2019\1\5 0005
 */
public class SendInChat {

    private String token;

    private InChatResponse frame;

    public SendInChat() {
    }

    public SendInChat(String token, InChatResponse frame) {
        this.token = token;
        this.frame = frame;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public InChatResponse getFrame() {
        return frame;
    }

    public void setFrame(InChatResponse frame) {
        this.frame = frame;
    }
}
