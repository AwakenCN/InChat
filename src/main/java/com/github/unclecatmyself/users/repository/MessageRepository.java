package com.github.unclecatmyself.users.repository;

import com.github.unclecatmyself.users.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MySelf on 2019/8/21.
 */
public interface MessageRepository extends JpaRepository<Message,Integer> {
}
