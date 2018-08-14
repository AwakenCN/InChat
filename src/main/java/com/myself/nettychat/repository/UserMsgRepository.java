package com.myself.nettychat.repository;

import com.myself.nettychat.dataobject.UserMsg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:06 2018\8\14 0014
 */
public interface UserMsgRepository extends JpaRepository<UserMsg,Integer> {
}
