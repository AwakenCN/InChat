package com.github.unclecatmyself.bootstrap.backmsg;

import com.github.unclecatmyself.common.utils.ConstansUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 列入项目中
 * Created by MySelf on 2018/11/23.
 */
public class InChatBackMapServiceImpl implements InChatBackMapService {


    public Map<String, String> loginSuccess() {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.LOGIN);
        backMap.put(ConstansUtil.SUCCESS,ConstansUtil.TRUE);
        return backMap;
    }


    public Map<String, String> loginError() {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.LOGIN);
        backMap.put(ConstansUtil.SUCCESS,ConstansUtil.FALSE);
        return backMap;
    }


    public Map<String, String> sendMe(String value) {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDME);
        backMap.put(ConstansUtil.VALUE,value);
        return backMap;
    }


    public Map<String, String> sendBack(String otherOne, String value) {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDTO);
        backMap.put(ConstansUtil.VALUE,value);
        backMap.put(ConstansUtil.ONE,otherOne);
        return backMap;
    }


    public Map<String, String> getMsg(String token, String value) {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDTO);
        backMap.put(ConstansUtil.FROM,token);
        backMap.put(ConstansUtil.VALUE,value);
        return backMap;
    }


    public Map<String, String> sendGroup(String token, String value, String groupId) {
        Map<String,String> backMap = new HashMap<String,String>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDGROUP);
        backMap.put(ConstansUtil.FROM,token);
        backMap.put(ConstansUtil.VALUE,value);
        backMap.put(ConstansUtil.GROUPID,groupId);
        return backMap;
    }

}
