package com.github.unclecatmyself.bootstrap.channel.protocol;

import com.alibaba.fastjson.JSONArray;

/**
 * 用户校验层
 * Created by MySelf on 2018/11/22.
 */
public interface InChatVerifyService {

    boolean verifyToken(String token);

    JSONArray getArrayByGroupId(String groupId);

}