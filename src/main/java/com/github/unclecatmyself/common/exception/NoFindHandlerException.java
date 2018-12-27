package com.github.unclecatmyself.common.exception;

/**
 * 找不到对应处理类
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class NoFindHandlerException extends RuntimeException {

    public NoFindHandlerException(String message) {
        super(message);
    }

}
