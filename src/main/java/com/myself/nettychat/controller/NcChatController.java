package com.myself.nettychat.controller;

import com.myself.nettychat.dataobject.UserMsg;
import com.myself.nettychat.repository.UserMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:32 2018\8\14 0014
 */
@Controller
@RequestMapping("/chat")
public class NcChatController {

    @Autowired
    private UserMsgRepository userMsgRepository;

    @GetMapping("/netty")
    public ModelAndView netty(@RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value = "size",defaultValue = "10") Integer size,
                              Map<String,Object> map){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<UserMsg> userMsgPage = userMsgRepository.findAll(pageable);
        //日期颠倒
        List<UserMsg> userMsgList = new ArrayList<>();
        for (int i = 0,j = userMsgPage.getContent().size()-1; i < userMsgPage.getContent().size();i++,j--){
            userMsgList.add(userMsgPage.getContent().get(j));
        }
        map.put("userMsgList",userMsgList);
        return new ModelAndView("h5",map);
    }

}
