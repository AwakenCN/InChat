package com.myself.nettychat.service;

import com.myself.nettychat.dataobject.User;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:52 2018\8\18 0018
 */
public interface UserService {

    User save(User user);

    User findByUserName(String userName);

    List<User> findAll();
}
