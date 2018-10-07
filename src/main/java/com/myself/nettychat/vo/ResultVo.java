package com.myself.nettychat.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * API 统一返回对象
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:58 2018\10\7 0007
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
