package com.github.unclecatmyself.user;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;

/**
 * Created by MySelf on 2018/12/19.
 */
public class InChatVerifyServiceImpl implements InChatVerifyService {

    @Override
    public boolean verifyToken(String token) {
        return true;
    }

    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return jsonArray;
    }
}
