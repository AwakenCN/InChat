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
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:01 2018\8\18 0018
 */
@RestController
@RequestMapping("/user")
public class NcLoginController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/to_register")
    public ResultVo toRegister(@RequestParam("username") String username,@RequestParam("password") String password){
        if (username.isEmpty() | password.isEmpty()){
            return ResultVOUtil.error(ResultConstant.PARAMS_CODE,ResultConstant.PARAMS_MSG);
        }
        //TODO 用户过多，需优化
        List<User> userList = userService.findAll();
        for (User item:userList){
            if (item.getUserName().equals(username)){
                return ResultVOUtil.error(ResultConstant.USER_NAME_REPART_CODE,ResultConstant.USER_NAME_REPART_MSG);
            }
        }
        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        userService.save(user);
        return ResultVOUtil.success("【账号注册成功】");
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
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
        try {
            User user = userService.findByUserName(form.getFUserName());
            if (user.getPassWord().equals(form.getFPassWord())){
                //登录成功
                String token = UUID.randomUUID().toString();
                //将token信息添加到系统缓存中
                TokenStore.add(token,user.getId());
                //将Token信息添加到Cookie中
                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
//                Sort sort = new Sort(Sort.Direction.DESC,"id");
//                Pageable pageable = new PageRequest(page-1,size,sort);
//                Page<UserMsg> userMsgPage = userMsgRepository.findAll(pageable);
//                //日期颠倒
//                List<UserMsg> userMsgList = new ArrayList<>();
//                for (int i = 0,j = userMsgPage.getContent().size()-1; i < userMsgPage.getContent().size();i++,j--){
//                    userMsgList.add(userMsgPage.getContent().get(j));
//                }
//                map.put("userMsgList",userMsgList);
//                map.put("userName",user.getUserName());
                return new ModelAndView(H5Constant.HOME);
            }else{
                map.put("msg","密码错误");
                return new ModelAndView(H5Constant.LOGIN_SUI,map);
            }
        }catch (Exception e){
            map.put("msg","用户不存在");
            return new ModelAndView(H5Constant.LOGIN_SUI,map);
        }
    }

}
