package com.github.unclecatmyself.bootstrap.verify;

import com.alibaba.fastjson.JSONArray;

/**
 * 用户校验层
 * Created by MySelf on 2018/11/22.
 */
public abstract class InChatVerifyService {

    public abstract boolean verifyToken(String token);

    public abstract JSONArray getArrayByGroupId(String groupId);

}