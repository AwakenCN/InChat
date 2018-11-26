package com.myself.nettychat.user;

import com.myself.nettychat.backmsg.InChatBackMapService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 不属于项目代码
 * Created by MySelf on 2018/11/23.
 */
@Service
public class InChatBackMapServiceImpl implements InChatBackMapService {

    @Override
    public Map<String, String> loginSuccess() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put("type","login");
        backMap.put("success","true");
        return backMap;
    }

    @Override
    public Map<String, String> loginError() {
        Map<String,String> backMap = new HashMap<>();
        backMap.put("type","login");
        backMap.put("success","false");
        return backMap;
    }

    @Override
    public Map<String, String> sendTo(String value) {
        Map<String,String> backMap = new HashMap<>();
        backMap.put("type","send");
        backMap.put("value",value);
        return backMap;
    }
}
