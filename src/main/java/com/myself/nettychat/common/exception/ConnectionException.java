package com.myself.nettychat.common.exception;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 连接异常
 **/
public class ConnectionException extends RuntimeException {

    public ConnectionException(String message) {
        super(message);
    }

}
