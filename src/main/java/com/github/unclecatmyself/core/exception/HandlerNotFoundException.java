package com.github.unclecatmyself.core.exception;

/**
 * 找不到对应处理类
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class HandlerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6724478022966267728L;

    public HandlerNotFoundException(String message) {
        super(message);
    }

}
