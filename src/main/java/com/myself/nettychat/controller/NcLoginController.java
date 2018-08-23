package com.myself.nettychat.controller;

import com.myself.nettychat.constont.CookieConstant;
import com.myself.nettychat.constont.H5Constant;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.dataobject.UserMsg;
import com.myself.nettychat.form.LoginForm;
import com.myself.nettychat.repository.UserMsgRepository;
import com.myself.nettychat.service.UserService;
import com.myself.nettychat.store.TokenStore;
import com.myself.nettychat.utils.CookieUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:01 2018\8\18 0018
 */
@Controller
@RequestMapping("/admin")
public class NcLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMsgRepository userMsgRepository;

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(Map<String,Object> map){
        return new ModelAndView(H5Constant.LOGIN);
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/regis")
    public ModelAndView register(){
        return new ModelAndView(H5Constant.LOGIN);
    }



    /**
     * 执行注册
     * @param form
     * @param bindingResult
     * @param response
     * @param map
     * @return
     */
    @PostMapping("/toRegister")
    public ModelAndView toRegister(@Valid LoginForm form, BindingResult bindingResult , HttpServletResponse response,
                                   Map<String, Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(H5Constant.LOGIN,map);
        }
        List<User> userList = userService.findAll();
        for (User item:userList){
            if (item.getUserName().equals(form.getUserName())){
                map.put("msg","用户名已存在，请重新填写唯一用户名");
                return new ModelAndView(H5Constant.LOGIN,map);
            }
        }
        User user = new User();
        BeanUtils.copyProperties(form,user);
        userService.save(user);
        map.put("userName",user.getUserName());
        map.put("passWord",user.getPassWord());
        return new ModelAndView(H5Constant.LOGIN,map);
    }

    /**
     * 登录判断
     * @return
     */
    @PostMapping("/toLogin")
    public ModelAndView toLogin(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "size",defaultValue = "10") Integer size,
                                @Valid LoginForm form, BindingResult bindingResult , HttpServletResponse response,
                                Map<String, Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(H5Constant.LOGIN,map);
        }
        try {
            User user = userService.findByUserName(form.getUserName());
            if (user.getPassWord().equals(form.getPassWord())){
                //登录成功
                String token = UUID.randomUUID().toString();
                //将token信息添加到系统缓存中
                TokenStore.add(token,user.getId());
                //将Token信息添加到Cookie中
                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
                Sort sort = new Sort(Sort.Direction.DESC,"id");
                Pageable pageable = new PageRequest(page-1,size,sort);
                Page<UserMsg> userMsgPage = userMsgRepository.findAll(pageable);
                //日期颠倒
                List<UserMsg> userMsgList = new ArrayList<>();
                for (int i = 0,j = userMsgPage.getContent().size()-1; i < userMsgPage.getContent().size();i++,j--){
                    userMsgList.add(userMsgPage.getContent().get(j));
                }
                map.put("userMsgList",userMsgList);
                map.put("userName",user.getUserName());
                return new ModelAndView(H5Constant.CHAT);
            }else{
                map.put("msg","密码错误");
                return new ModelAndView(H5Constant.LOGIN,map);
            }
        }catch (Exception e){
            map.put("msg","用户不存在");
            return new ModelAndView(H5Constant.LOGIN,map);
        }
    }

}
