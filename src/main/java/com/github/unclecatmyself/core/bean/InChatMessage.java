package com.github.unclecatmyself.core.bean;

/**
 * 用户层消息Bean封装
 * Created by MySelf on 2018/12/19.
 */
public class InChatMessage {

    /** 消息时间 */
    private int time;

    /** 消息类型 */
    private String type;

    /** 消息值 */
    private String value;

    /** 用户标识 */
    private String token;

    /** 群聊Id */
    private String groupId;

    /** 是否在线-个人 */
    private String online;

    /** 是否在线-群聊 */
    private String onlineGroup;

    /** 消息接收人标识 */
    private String one;

    public String getOnlineGroup() {
        return onlineGroup;
    }

    public void setOnlineGroup(String onlineGroup) {
        this.onlineGroup = onlineGroup;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
                ", groupId='" + groupId + '\'' +
                ", online='" + online + '\'' +
                ", onlineGroup='" + onlineGroup + '\'' +
                ", one='" + one + '\'' +
                '}';
    }
}
