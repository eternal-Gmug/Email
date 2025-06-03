package org.example.master.repository;

import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<MailEntity,Long> {
    //自定义访问方法
    List<MailEntity> findByFromUser(UserEntity fromUser);
}
