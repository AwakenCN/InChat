package com.github.unclecatmyself.core.exception;

/**
 * 未找到正常注册的连接异常
 * Create by UncleCatMySelf in 13:58 2018\12\30 0030
 */
public class LoginChannelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2614068393411382075L;

    public LoginChannelNotFoundException(String message) {
        super(message);
    }
}
