package com.github.unclecatmyself.common.utils;

import com.github.unclecatmyself.common.constant.UtilConstant;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by MySelf on 2019/1/8.
 */
public class SslUtil {

    private static volatile SSLContext sslContext = null;

    public static SSLContext createSSLContext(String type ,String path ,String password) throws Exception {
        if(null == sslContext){
            synchronized (SslUtil.class) {
            if(null == sslContext){
                KeyStore ks = KeyStore.getInstance(type); /// "JKS"  　　　　  　　
                InputStream ksInputStream = new FileInputStream(UtilConstant.PATH_PREFIX+path); /// 证书存放地址
                ks.load(ksInputStream, password.toCharArray());
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(ks, password.toCharArray());
                sslContext = SSLContext.getInstance(UtilConstant.INSTANT);
                sslContext.init(kmf.getKeyManagers(), null, null);
                }
            }
        }
        return sslContext;
    }

}
