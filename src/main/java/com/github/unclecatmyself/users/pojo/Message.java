package com.github.unclecatmyself.users.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MySelf on 2019/8/21.
 */
@Entity
@Data
@DynamicUpdate
public class Message {

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 消息时间 */
    private Date time;

    /** 消息类型 */
    private String type;

    /** 消息值 */
    private String value;

    /** 用户标识 */
    private String token;

    /** 群聊Id */
    private String groudId;

    /** 是否在线-个人 */
    private String online;

    /** 是否在线-群聊 */
    private String onlineGroup;

    /** 消息接收人标识 */
    private String one;

}
