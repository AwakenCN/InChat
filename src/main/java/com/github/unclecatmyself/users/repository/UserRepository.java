package com.github.unclecatmyself.users.repository;

import com.github.unclecatmyself.users.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName UserRepository
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/21 22:37
 * @Version 1.0
 **/
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

}
