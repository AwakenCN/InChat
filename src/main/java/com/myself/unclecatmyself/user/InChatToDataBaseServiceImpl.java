package com.myself.unclecatmyself.user;

import com.myself.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 不属于项目文件
 * Created by MySelf on 2018/12/3.
 */
@Service
public class InChatToDataBaseServiceImpl implements InChatToDataBaseService {
    @Override
    public Boolean writeMapToDB(Map<String, Object> maps) {
        //TODO  写入数据库
        return true;
    }
}
