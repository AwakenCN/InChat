package com.github.unclecatmyself.core.bean.vo;

/**
 * Create by UncleCatMySelf in 11:11 2018\12\31 0031
 */
public class ResultVO<T> {

    private int code;

    private T data;

    public ResultVO(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
