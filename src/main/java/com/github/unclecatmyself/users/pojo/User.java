package com.github.unclecatmyself.users.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName User
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/21 22:33
 * @Version 1.0
 **/
//@Entity
//@Data
//@DynamicUpdate
public class User {

    /**id*/
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

}
