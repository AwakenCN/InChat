package com.github.unclecatmyself.core.bean;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 16:06 2019\1\5 0005
 */
public class SendInChat {

    private String token;

    private Map<String,String> frame;

    public SendInChat() {
    }

    public SendInChat(String token, Map<String, String> frame) {
        this.token = token;
        this.frame = frame;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getFrame() {
        return frame;
    }

    public void setFrame(Map<String, String> frame) {
        this.frame = frame;
    }
}
