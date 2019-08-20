package com.github.unclecatmyself.users.repository;

import com.github.unclecatmyself.users.pojo.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName TestUserRepository
 * @Description Jpa映射层
 * @Author MySelf
 * @Date 2019/7/27 11:05
 * @Version 1.0
 **/
public interface TestRepository extends JpaRepository<Test,Integer> {

}
