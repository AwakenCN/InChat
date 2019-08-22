package com.github.unclecatmyself.users;

import com.alibaba.fastjson.JSONArray;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.common.utils.SpringContextUtils;
import com.github.unclecatmyself.users.pojo.User;

/**
 * Created by MySelf on 2019/1/3.
 */
public class VerifyServiceImpl implements InChatVerifyService {

//    private UserRepository repository = (UserRepository) SpringContextUtils.getBean(UserRepository.class);

    public boolean verifyToken(String token) {
//        User user = repository.findByUsername(token);
//        if (user.getId() != null){
//            return true;
//        }
//        return false;
        return true;
    }

    public JSONArray getArrayByGroupId(String groupId) {
        JSONArray jsonArray = JSONArray.parseArray("[\"1111\",\"2222\",\"3333\"]");
        return jsonArray;
    }
}
