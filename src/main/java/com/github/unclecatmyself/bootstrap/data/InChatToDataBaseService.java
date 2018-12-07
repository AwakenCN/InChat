package com.github.unclecatmyself.bootstrap.data;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/3.
 */
public interface InChatToDataBaseService {

    Boolean writeMapToDB(Map<String,Object> maps);

}
