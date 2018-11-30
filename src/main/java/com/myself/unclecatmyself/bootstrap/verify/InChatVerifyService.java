package com.myself.unclecatmyself.bootstrap.verify;

/**
 * 用户校验层
 * Created by MySelf on 2018/11/22.
 */
public interface InChatVerifyService {

    boolean verifyToken(String token);

    String getVerifyLogin();
}