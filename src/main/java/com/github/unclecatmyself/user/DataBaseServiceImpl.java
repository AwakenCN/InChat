package com.github.unclecatmyself.user;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/19.
 */
public class DataBaseServiceImpl implements InChatToDataBaseService{

    @Override
    public Boolean writeMapToDB(Map<String, Object> maps) {
        System.out.println(maps.toString());
        return true;
    }
}
