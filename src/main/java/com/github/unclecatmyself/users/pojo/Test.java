package com.github.unclecatmyself.users.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName TestUser
 * @Description 框架测试用户，测试密码加密，不允许生产使用
 * @Author MySelf
 * @Date 2019/7/27 11:06
 * @Version 1.0
 **/
@Entity
@Data
@DynamicUpdate
public class Test implements Serializable {

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**信息*/
    private String msg;


}
