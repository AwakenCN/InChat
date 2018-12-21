package com.github.unclecatmyself.common.bean;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MySelf on 2018/12/19.
 */
public class InChatMessage {

    private Date time;

    private String type;

    private String value;

    private String token;

    private String groudId;

    private String online;

    private ArrayList onlineGroup;

    private String one;

    public ArrayList getOnlineGroup() {
        return onlineGroup;
    }

    public void setOnlineGroup(ArrayList onlineGroup) {
        this.onlineGroup = onlineGroup;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroudId() {
        return groudId;
    }

    public void setGroudId(String groudId) {
        this.groudId = groudId;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    @Override
    public String toString() {
        return "InChatMessage{" +
                "time=" + time +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", token='" + token + '\'' +
                ", groudId='" + groudId + '\'' +
                ", online='" + online + '\'' +
                ", onlineGroup=" + onlineGroup +
                ", one='" + one + '\'' +
                '}';
    }
}
