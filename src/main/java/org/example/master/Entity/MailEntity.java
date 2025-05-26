package org.example.master.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

//邮件实体类
public class MailEntity {

    //邮件的ID号
    private Long email_id;

    private UserEntity from_user;

    private UserEntity to_user;

    private String from_email;

    private String to_email;

    //邮件的标题
    private String subject;

    //邮件的主体内容
    private String content;

    //邮件发送时间
    private LocalDateTime send_time;

    //邮件是否已读
    private boolean is_read;

    //邮件的归属邮箱
    private InboxEntity folder;

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
    /*public Long getToId() {
        return to_user.getUserId();
    }*/

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

    public InboxEntity getFolder() {
        return folder;
    }

    public void setFolder(InboxEntity folder) {
        this.folder = folder;
    }

}
