package com.github.unclecatmyself.support;

import com.github.unclecatmyself.bootstrap.channel.protocol.Response;
import com.github.unclecatmyself.core.constant.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 列入项目中，默认返回实现
 * Created by MySelf on 2018/11/23.
 */
public class InChatResponse implements Response {

    public Map<String, String> loginSuccess() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.LOGIN);
        backMap.put(Constants.SUCCESS, Constants.TRUE);
        return backMap;
    }

    public Map<String, String> loginError() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.LOGIN);
        backMap.put(Constants.SUCCESS, Constants.FALSE);
        return backMap;
    }

    public Map<String, String> sendMe(String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.SEND_ME);
        backMap.put(Constants.VALUE,value);
        return backMap;
    }

    public Map<String, String> sendBack(String otherOne, String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.SEND_TO);
        backMap.put(Constants.VALUE,value);
        backMap.put(Constants.ONE,otherOne);
        return backMap;
    }


    public Map<String, String> getMessage(String token, String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.SEND_TO);
        backMap.put(Constants.FROM,token);
        backMap.put(Constants.VALUE,value);
        return backMap;
    }


    public Map<String, String> sendGroup(String token, String value, String groupId) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(Constants.TYPE, Constants.SEND_GROUP);
        backMap.put(Constants.FROM,token);
        backMap.put(Constants.VALUE,value);
        backMap.put(Constants.GROUP_ID,groupId);
        return backMap;
    }

}
