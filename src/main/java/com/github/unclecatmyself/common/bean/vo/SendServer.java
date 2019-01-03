package com.github.unclecatmyself.common.bean.vo;

import com.github.unclecatmyself.common.utils.ConstansUtil;

/**
 * Created by MySelf on 2019/1/3.
 */
public class SendServer {

    private String type = ConstansUtil.SERVER;

    private String value;

    public SendServer(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
