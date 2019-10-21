package com.github.unclecatmyself.core.utils;

import com.github.unclecatmyself.core.bean.vo.SendServerVO;
import com.github.unclecatmyself.core.constant.Constants;
import com.github.unclecatmyself.core.constant.HttpConstant;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import java.io.*;

/**
 * HTTP接口处理方法
 * Create by UncleCatMySelf in 22:43 2018\12\30 0030
 */
public class HttpUtil {

    public static String checkType(FullHttpRequest msg){
        msg.retain();
        String url = msg.uri();
        System.out.println(url);
        HttpMethod method = msg.method();
        String meName = method.name();
        if (url.equals(HttpConstant.URI_GET_SIZE) && meName.equals(HttpConstant.GET)){
            return HttpConstant.GET_SIZE;
        }else if (url.equals(HttpConstant.URI_SEND_FROM_SERVER) && meName.equals(HttpConstant.POST)){
            return HttpConstant.SEND_FROM_SERVER;
        }else if (url.equals(HttpConstant.URI_GET_LIST) && meName.equals(HttpConstant.GET)){
            return HttpConstant.GET_LIST;
        }else if (url.equals(HttpConstant.URI_GET_STATE) && meName.equals(HttpConstant.POST)){
            return HttpConstant.GET_STATE;
        }else if (url.equals(HttpConstant.URI_SEND_IN_CHAT) && meName.equals(HttpConstant.POST)){
            return HttpConstant.SEND_IN_CHAT;
        }else {
            return HttpConstant.NOT_FIND_URI;
        }
    }

    public static SendServerVO getToken(FullHttpRequest msg) throws UnsupportedEncodingException {
        msg.retain();
        SendServerVO sendServerVO = new SendServerVO();
        String content = msg.content().toString(CharsetUtil.UTF_8);
        String[] stars = content.split("&");
        for (int i=0,len=stars.length;i<len;i++){
            String item = stars[i].toString();
            String firstType = item.substring(0,5);
            String value = item.substring(6,item.length());
            if (Constants.TOKEN.equals(firstType)){
                //Token
                sendServerVO.setToken(value);
            }else if(Constants.VALUE.endsWith(firstType)){
                //value
                sendServerVO.setValue(value);
            }
        }
        return sendServerVO;
    }


}
