package com.github.unclecatmyself.core.bean.vo;

/**
 * @ClassName StateVo
 * @Description TODO
 * @Author MySelf
 * @Date 2019/10/15 23:27
 * @Version 1.0
 **/
public class StateVo {

    public String token;

    public Boolean state;

    public StateVo() {
    }

    public StateVo(String token, Boolean state) {
        this.token = token;
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
