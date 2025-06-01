package org.example.master.services;

import jakarta.transaction.Transactional;
import org.example.master.Entity.AttachmentEntity;
import org.example.master.repository.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    //用于存储邮件中临时上传的附件，准备在邮件发送时持久化进数据表
    public static Map<String,AttachmentEntity> attachmentTempFolder;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


}
