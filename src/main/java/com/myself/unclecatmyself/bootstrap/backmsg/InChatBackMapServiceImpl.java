package com.myself.unclecatmyself.bootstrap.backmsg;

import com.myself.unclecatmyself.common.utils.ConstansUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 列入项目中
 * Created by MySelf on 2018/11/23.
 */
@Service
public class InChatBackMapServiceImpl implements InChatBackMapService {

    @Override
    public Map<String, String> loginSuccess() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.LOGIN);
        backMap.put(ConstansUtil.SUCCESS,ConstansUtil.TRUE);
        return backMap;
    }

    @Override
    public Map<String, String> loginError() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.LOGIN);
        backMap.put(ConstansUtil.SUCCESS,ConstansUtil.FALSE);
        return backMap;
    }

    @Override
    public Map<String, String> sendMe(String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDME);
        backMap.put(ConstansUtil.VALUE,value);
        return backMap;
    }

    @Override
    public Map<String, String> sendBack(String otherOne, String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDTO);
        backMap.put(ConstansUtil.VALUE,value);
        backMap.put(ConstansUtil.ONE,otherOne);
        return backMap;
    }

    @Override
    public Map<String, String> getMsg(String me, String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDTO);
        backMap.put(ConstansUtil.FROM,me);
        backMap.put(ConstansUtil.VALUE,value);
        return backMap;
    }

    @Override
    public Map<String, String> sendGroup(String me, String value, String groupId) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put(ConstansUtil.TYPE,ConstansUtil.SENDGROUP);
        backMap.put(ConstansUtil.FROM,me);
        backMap.put(ConstansUtil.VALUE,value);
        backMap.put(ConstansUtil.GROUPID,groupId);
        return backMap;
    }

}
