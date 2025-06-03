package org.example.master.services;

import jakarta.transaction.Transactional;
import org.example.master.Entity.AttachmentEntity;
import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
import org.example.master.repository.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentService {
    //用于存储邮件中临时上传的附件，准备在邮件发送时持久化进数据表,一个用户用用户标识对应一个附件列表（类似于核心缓存）
    public static Map<Long,Map<String,AttachmentEntity>> attachmentTempFolder;
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public AttachmentEntity createEntity(String fileName, String filePath, UserEntity user){
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setUser(user);
        attachment.setFilename(fileName);
        attachment.setFilepath(filePath);
        attachment.setUploadTime(LocalDateTime.now());
        return attachment;
    }

    public void saveAttachmentsWithEmail(Long userId, MailEntity mail){
        Collection<AttachmentEntity> sentAttachments = attachmentTempFolder.get(userId).values();
        for(AttachmentEntity attachment:sentAttachments){
            attachment.setMail(mail);
            //持久化进数据表
            attachmentRepository.save(attachment);
            //持久化进数据表后清空缓存附件文件夹
            attachmentTempFolder.get(userId).clear();
        }
    }
}
