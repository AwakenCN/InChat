package com.github.unclecatmyself.users;

import com.github.unclecatmyself.common.utils.SpringContextUtils;
import com.github.unclecatmyself.task.TextData;
import com.github.unclecatmyself.users.pojo.Test;
import com.github.unclecatmyself.users.repository.TestRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by MySelf on 2019/8/20.
 */
public class UserTextData extends TextData {

    private TestRepository repository = (TestRepository) SpringContextUtils.getBean(TestRepository.class);

    @Override
    public void writeData(Map<String, Object> maps) {
        Test test = new Test();
        test.setId(1);
        test.setMsg("1111");
        repository.save(test);
    }
}
