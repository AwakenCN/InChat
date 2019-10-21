package com.github.unclecatmyself.core.bean.vo;

import java.util.Set;

/**
 * Create by UncleCatMySelf in 12:26 2018\12\31 0031
 */
public class GetListVO {

    private Set<String> tokens;

    public GetListVO(Set<String> tokens) {
        this.tokens = tokens;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    public void setTokens(Set<String> tokens) {
        this.tokens = tokens;
    }
}
