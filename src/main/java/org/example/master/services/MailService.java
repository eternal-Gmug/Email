package org.example.master.services;

import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
import org.example.master.repository.MailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MailService {
    private final MailRepository mailRepository;

    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    //发送邮件创建邮件实体类
    public MailEntity sendEmail(UserEntity fromUser, UserEntity toUser, String subject, String body){
        //新建一个邮件实体类
        MailEntity mail = new MailEntity();
        mail.setFromUser(fromUser);
        mail.setToUser(toUser);
        mail.setFromEmail(fromUser.getEmail());
        mail.setToEmail(toUser.getEmail());
        mail.setSubject(subject);
        mail.setBody(body);
        mail.setSentDate(LocalDateTime.now());
        mail.setRead(false);
        //可能需要设置收件箱
        //处理附件
        if(AttachmentService.attachmentTempFolder.isEmpty()) {
            //如果没有附件，将邮件的附件个数置为0
            mail.setHasAttachment(0);
        }else{
            //如果有附件，附件个数置为现有个数
            mail.setHasAttachment(AttachmentService.attachmentTempFolder.size());
        }
        //返回一个持久化的mail实体对象,注意此时还没有发送附件
        return mailRepository.save(mail);
    }

}
