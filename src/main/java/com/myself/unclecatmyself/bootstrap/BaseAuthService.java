package com.myself.unclecatmyself.bootstrap;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 权限校验
 **/
public interface BaseAuthService {

    boolean  authorized(String username,String password);

}
