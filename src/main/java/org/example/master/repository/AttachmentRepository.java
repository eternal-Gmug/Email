package org.example.master.repository;

import org.example.master.Entity.AttachmentEntity;
import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity,Long> {
    //自定义查找方法
    List<AttachmentEntity> findByMail(MailEntity mail);
    List<AttachmentEntity> findByUser(UserEntity user);
}
