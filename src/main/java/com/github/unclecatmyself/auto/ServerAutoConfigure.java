package com.github.unclecatmyself.auto;

import com.github.unclecatmyself.common.bean.InitNetty;
import org.apache.commons.lang3.ObjectUtils;


/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class ServerAutoConfigure {

    private static  final  int _BLACKLOG =   1024;

    private static final  int  CPU =Runtime.getRuntime().availableProcessors();

    private static final  int  SEDU_DAY =10;

    private static final  int TIMEOUT =120;

    private static final  int BUF_SIZE=10*1024*1024;


    public ServerAutoConfigure(){

    }

    public InitServer initServer(InitNetty serverBean){
        if(!ObjectUtils.allNotNull(serverBean.getWebport(),serverBean.getServerName())){
            throw  new NullPointerException("not set port");
        }
        if(serverBean.getBacklog()<1){
            serverBean.setBacklog(_BLACKLOG);
        }
        if(serverBean.getBossThread()<1){
            serverBean.setBossThread(CPU);
        }
        if(serverBean.getInitalDelay()<0){
            serverBean.setInitalDelay(SEDU_DAY);
        }
        if(serverBean.getPeriod()<1){
            serverBean.setPeriod(SEDU_DAY);
        }
        if(serverBean.getHeart()<1){
            serverBean.setHeart(TIMEOUT);
        }
        if(serverBean.getRevbuf()<1){
            serverBean.setRevbuf(BUF_SIZE);
        }
        if(serverBean.getWorkerThread()<1){
            serverBean.setWorkerThread(CPU*2);
        }
        return new InitServer(serverBean);
    }

}
