package com.myself.unclecatmyself.user;

import com.alibaba.fastjson.JSONArray;
import com.myself.unclecatmyself.bootstrap.verify.InChatVerifyService;
import org.springframework.stereotype.Service;

/**
 * 不属于项目代码
 * Created by MySelf on 2018/11/22.
 */
@Service
public class InChatVerifyServiceImpl implements InChatVerifyService {

    @Override
    public boolean verifyToken(String token) {
        //与Redis中的Token做比较，请用户自己实现,查找是否存在该Token值
//        log.info("verify---"+token);
        return true;
    }

    @Override
    public JSONArray getArrayByGroupId(String groupId) {
        return JSONArray.parseArray("['1111','2222','3333']");
    }

}
