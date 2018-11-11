package com.myself.nettychat.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by MySelf on 2018/11/6.
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -1020280450330091843L;

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;

}
