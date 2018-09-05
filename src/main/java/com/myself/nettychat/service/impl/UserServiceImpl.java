package com.myself.nettychat.service.impl;

import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.repository.UserRepository;
import com.myself.nettychat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:52 2018\8\18 0018
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public User findOne(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
