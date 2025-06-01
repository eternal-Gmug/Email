package org.example.master.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//附件实体类
@Entity
@Table(name = "attachment")
public class AttachmentEntity {
    @Id
    //附件的唯一标识（重要变量）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachment_id;

    //附件的名称
    @Column(name = "filename",nullable = false)
    private String filename;

    //附件的文件路径
    @Column(name = "filepath",nullable = false)
    private String filepath;

    //附件是隶属于哪个邮件的(重要变量）
    @ManyToOne
    @JoinColumn(name = "email_id",nullable = false)
    @JsonBackReference("mail-attachments")
    private MailEntity mail;

    //附件是哪个用户发送的
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    //附件上传时间
    @Column(name = "upload_time",nullable = false)
    private LocalDateTime uploadTime;

    // Getters and Setters
    public Long getAttachment_id() {
        return attachment_id;
    }
    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity userBelong){
        user = userBelong;
    }
    public MailEntity getMail() {
        return mail;
    }
    public void setMail(MailEntity mail) {
        this.mail = mail;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}
