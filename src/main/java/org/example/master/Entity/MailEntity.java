package org.example.master.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mail")
//邮件实体类
public class MailEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   //邮件的唯一标识
    private Long email_id;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    @JsonBackReference("from-user-emails")
    private UserEntity from_user;

    @ManyToOne
    @JoinColumn(name = "to_id",nullable = false)
    @JsonBackReference("to-user-emails")
    private UserEntity to_user;

    @Column(name = "from_email",nullable = false)
    private String from_email;

    @Column(name = "to_email", nullable = false)
    private String to_email;

    //邮件的标题
    @Column(name = "subject")
    private String subject;

    //邮件的主体内容
    @Column(name = "content")
    private String content;

    //邮件发送时间
    @Column(name = "send_time")
    private LocalDateTime send_time;

    //邮件是否已读
    @Column(name = "is_read")
    private boolean is_read;

    //邮件是否有附件
    @Column(name = "has_attachment",nullable = false)
    private int hasAttachment;

    //邮件的归属邮箱
    //private InboxEntity folder;

    //邮件的附件集
    //mapped by定义了关系的拥有方（数据库中外键所在的表），cascade定义了级联操作的行为，orphanRemoval定义了“孤儿删除”的行为
    @OneToMany(mappedBy = "mail",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("mail-attachments")
    private List<AttachmentEntity> attachments = new ArrayList<>();

    //添加附件的辅助方法
    public void addAttachment(AttachmentEntity attachment) {
        attachment.setMail(this);
        attachments.add(attachment);
    }

    //Getters and setters

    public Long getEmail_id() { return email_id; }
    public void setEmail_id(Long emailID){
        this.email_id = emailID;
    }

    public UserEntity getFromUser() {
        return from_user;
    }

    public void setFromUser(UserEntity fromUser) {
        this.from_user = fromUser;
    }

    public UserEntity getToUser() {
        return to_user;
    }

    public void setToUser(UserEntity toUser) {
        this.to_user = toUser;
    }

    public String getFromEmail() {
        return from_email;
    }

    public void setFromEmail(String fromEmail) {
        this.from_email = fromEmail;
    }

    public String getToEmail() {
        return to_email;
    }

    public void setToEmail(String toEmail) {
        this.to_email = toEmail;
    }

    //得到到达用户的ID和名称
    public Long getToId() {
        return to_user.getUserid();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return content;
    }

    public void setBody(String body) {
        this.content = body;
    }

    public LocalDateTime getSentDate() {
        return send_time;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.send_time = sentDate;
    }

    public boolean isRead() {
        return is_read;
    }

    public void setRead(boolean isRead) {
        this.is_read = isRead;
    }

    public int isHasAttachment(){
        return hasAttachment;
    }

    public void setHasAttachment(int hasAttachment) {
        this.hasAttachment = hasAttachment;
    }
/*
    public InboxEntity getFolder() {
        return folder;
    }

    public void setFolder(InboxEntity folder) {
        this.folder = folder;
    }
*/
}
