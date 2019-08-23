package com.github.unclecatmyself.users;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.common.utils.SpringContextUtils;
import com.github.unclecatmyself.users.pojo.User;
import com.github.unclecatmyself.users.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by MySelf on 2019/1/3.
 */
@Component
@Data
public class VerifyServiceImpl extends InChatVerifyService {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void init(){
        this.repository = repository;
    }

    @Override
    public boolean verifyToken(String token) {
        init();
        User user = repository.findByUsername(token);
        if (user.getId() != null){
            return true;
        }
        return false;
    }


    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        init();
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return null;
    }
}
