package com.myself.nettychat.bootstrap.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 遗嘱消息
 **/
@Builder
@Data
public class WillMeaasge {

    private String willTopic;

    private String willMessage;

    private  boolean isRetain;

    private int qos;

}
