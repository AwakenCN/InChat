package com.github.unclecatmyself.core.bean;

import com.github.unclecatmyself.bootstrap.channel.protocol.Response;
import com.github.unclecatmyself.core.constant.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 列入项目中，默认返回实现
 * Created by MySelf on 2018/11/23.
 */
public class InChatResponse implements Response {

    private String type;
    private String result;
    private String value;
    private String one;
    private String from;
    private String group;

    public InChatResponse loginSuccess() {
        setType(Constants.LOGIN);
        setResult(Constants.TRUE);
        return this;
    }

    public InChatResponse loginError() {
        setType(Constants.LOGIN);
        setResult(Constants.FALSE);
        return this;
    }

    public InChatResponse sendMe(String value) {
        setType(Constants.SEND_ME);
        setValue(value);
        return this;
    }

    public InChatResponse sendBack(String otherOne, String value) {
        Map<String, String> backMap = new HashMap<>();
        setType(Constants.SEND_TO);
        setValue(value);
        setOne(otherOne);
        return this;
    }


    public InChatResponse getMessage(String token, String value) {
        setType(Constants.SEND_TO);
        setFrom(token);
        setValue(value);
        return this;
    }

    public InChatResponse sendGroup(String token, String value, String groupId) {
        setType(Constants.SEND_GROUP);
        setFrom(token);
        setValue(value);
        setGroup(groupId);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
