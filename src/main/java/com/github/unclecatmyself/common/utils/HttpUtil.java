package com.github.unclecatmyself.common.utils;

import com.github.unclecatmyself.common.bean.vo.SendServerVO;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * HTTP接口处理方法
 * Create by UncleCatMySelf in 22:43 2018\12\30 0030
 */
public class HttpUtil {

    public static String checkType(FullHttpRequest msg){
        msg.retain();
        String url = msg.uri();
        HttpMethod method = msg.method();
        String meName = method.name();
        if (url.equals(HttpConstantUtil.URI_GETSIZE) && meName.equals(HttpConstantUtil.GET)){
            return HttpConstantUtil.GETSIZE;
        }else if (url.equals(HttpConstantUtil.URI_SENDFROMSERVER) && meName.equals(HttpConstantUtil.POST)){
            return HttpConstantUtil.SENDFROMSERVER;
        }else if (url.equals(HttpConstantUtil.URI_GETLIST) && meName.equals(HttpConstantUtil.GET)){
            return HttpConstantUtil.GETLIST;
        }else {
            return HttpConstantUtil.NOTFINDURI;
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
            if (ConstansUtil.TOKEN.equals(firstType)){
                //Token
                sendServerVO.setToken(value);
            }else if(ConstansUtil.VALUE.endsWith(firstType)){
                //value
                sendServerVO.setValue(value);
            }
        }
        return sendServerVO;
    }
}
