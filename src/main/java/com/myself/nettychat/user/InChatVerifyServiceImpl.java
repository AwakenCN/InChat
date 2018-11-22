package com.myself.nettychat.user;

import com.myself.nettychat.verify.InChatVerifyService;
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
        System.out.println("verify---"+token);
        if ("3333".equals(token)){
            return true;
        }
        return false;
    }
}
