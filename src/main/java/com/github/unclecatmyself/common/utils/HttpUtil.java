package com.github.unclecatmyself.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
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

    public static String getToken(FullHttpRequest msg){
        msg.retain();
        String url = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println(url);
        return null;
    }
}
