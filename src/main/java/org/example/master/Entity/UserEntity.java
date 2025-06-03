package org.example.master.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    //@ID用于标识主键字段
    @Id
    //定义主键生成策略，由数据库自动生成，当实体写入数据表后才填充（重要变量）
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    //@JsonIgnore用于在序列化和反序列化过程中忽略指定字段，常用于敏感信息的隐藏
    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    //用户创建时间
    @Column(name = "creation_time",nullable = false)
    private LocalDateTime createTime;

    //用户上次登录时间
    @Column(name = "update_time",nullable = false)
    private LocalDateTime updateTime;

    //记录发送邮件列表
    @OneToMany(mappedBy = "from_user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("from-user-emails")
    private List<MailEntity> sentEmails;

    //记录接受邮件
    @OneToMany(mappedBy = "to_user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("to-user-emails")
    private List<MailEntity> receivedEmails;

    //用户的附件文件夹下载路径
    @Column(name = "attachmentFolder_path")
    private String attachmentFolderPath;

    //会在一个实体被持久化前执行
    @PrePersist
    void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    //会在一个实体被更改以后执行
    @PreUpdate
    void onUpdate(){
        this.updateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getAttachmentFolderPath() {
        return attachmentFolderPath;
    }

    public void setAttachmentFolderPath(String attachmentFolderPath) {
        this.attachmentFolderPath = attachmentFolderPath;
    }

    public List<MailEntity> getSentEmails() {
        return sentEmails;
    }

    public void setSentEmails(List<MailEntity> sentEmails) {
        this.sentEmails = sentEmails;
    }

    public List<MailEntity> getReceivedEmails() {
        return receivedEmails;
    }

    public void setReceivedEmails(List<MailEntity> receivedEmails) {
        this.receivedEmails = receivedEmails;
    }
}
