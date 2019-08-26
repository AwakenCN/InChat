package com.github.unclecatmyself.user;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;

/**
 * Created by MySelf on 2019/8/26.
 */
public class VerifyServiceImpl implements InChatVerifyService {

    public boolean verifyToken(String token) {
        return true;
    }

    public JSONArray getArrayByGroupId(String groupId) {
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return jsonArray;
    }

}
