package com.myself.nettychat.controller;

import com.myself.nettychat.common.utils.ResultVOUtil;
import com.myself.nettychat.constont.CookieConstant;
import com.myself.nettychat.constont.H5Constant;
import com.myself.nettychat.constont.ResultConstant;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.form.LoginForm;
import com.myself.nettychat.repository.UserMsgRepository;
import com.myself.nettychat.service.UserService;
import com.myself.nettychat.store.TokenStore;
import com.myself.nettychat.common.utils.CookieUtil;
import com.myself.nettychat.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户账号模块
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:01 2018\8\18 0018
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class NcLoginController {

    @Autowired
    private UserService userService;

    /**
     * 【账号注册】-2018-11-2
     * @param username 用户名
     * @param password 密码
     * @return {@link ResultVo}
     */
    @PostMapping(value = "/to_register")
    public ResultVo toRegister(@RequestParam("username") String username,@RequestParam("password") String password){
        //参数非空判断，如前端实现，可省略
        if (username.isEmpty() | password.isEmpty()){
            log.info("user-【参数为空】");
            return ResultVOUtil.error(ResultConstant.PARAMS_CODE,ResultConstant.PARAMS_MSG);
        }
        //TODO 用户过多，需优化
        List<User> userList = userService.findAll();
        for (User item:userList){
            if (item.getUserName().equals(username)){
                log.info("user-【用户名已存在】-"+username);
                return ResultVOUtil.error(ResultConstant.USER_NAME_REPART_CODE,ResultConstant.USER_NAME_REPART_MSG);
            }
        }
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        userService.save(user);
        log.info("user-【账号注册成功】-"+username);
        return ResultVOUtil.success("【账号注册成功】");
    }


    @PostMapping(value = "/to_login")
    public ResultVo toLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        //参数非空判断，如前端实现，可省略
        if (username.isEmpty() | password.isEmpty()){
            return ResultVOUtil.error(ResultConstant.PARAMS_CODE,ResultConstant.PARAMS_MSG);
        }
//        try {
//            User user = userService.findByUserName(form.getFUserName());
//            if (user.getPassWord().equals(form.getFPassWord())){
//                //登录成功
//                String token = UUID.randomUUID().toString();
//                //将token信息添加到系统缓存中
//                TokenStore.add(token,user.getId());
//                //将Token信息添加到Cookie中
//                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
//                return new ModelAndView(H5Constant.HOME);
//            }else{
//                map.put("msg","密码错误");
//                return new ModelAndView(H5Constant.LOGIN_SUI,map);
//            }
//        }catch (Exception e){
//
//            return new ModelAndView(H5Constant.LOGIN_SUI,map);
//        }
        return ResultVOUtil.success();
    }

}
