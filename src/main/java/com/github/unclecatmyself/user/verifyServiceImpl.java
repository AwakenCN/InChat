package com.github.unclecatmyself.user;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;


/**
 * Created by MySelf on 2018/12/14.
 */
public class verifyServiceImpl implements InChatVerifyService {


    @Override
    public boolean verifyToken(String token) {
        //登录校验
        return true;
    }

    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        //根据群聊id获取对应的群聊人员ID
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return jsonArray;
    }
}
