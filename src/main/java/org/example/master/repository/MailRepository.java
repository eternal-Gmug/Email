package org.example.master.repository;

import org.example.master.Entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailEntity,Long> {
    //自定义访问方法

}
