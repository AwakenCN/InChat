package com.myself.nettychat.repository;

import com.myself.nettychat.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:47 2018\8\13 0013
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserName(String userName);

}
