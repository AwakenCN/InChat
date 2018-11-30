package com.myself.unclecatmyself.common.exception;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 找不到Handler异常
 **/
public class NoFindHandlerException extends RuntimeException {

    public NoFindHandlerException(String message) {
        super(message);
    }

}
