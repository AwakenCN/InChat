package com.github.unclecatmyself.core.bean.vo;

import java.util.Date;

/**
 * Create by UncleCatMySelf in 11:13 2018\12\31 0031
 */
public class GetSizeVO {

    private Integer online;

    private Date time;

    public GetSizeVO(Integer online, Date time) {
        this.online = online;
        this.time = time;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
