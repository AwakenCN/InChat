package com.github.unclecatmyself.bootstrap.channel.http;

/**
 * Created by MySelf on 2019/1/2.
 */
public interface FromServerService<Integer> {

    Integer getCode();

    String findByCode(Integer code);
}
