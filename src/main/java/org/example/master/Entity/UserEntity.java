package org.example.master.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class UserEntity {
    //@ID用于标识主键字段
    @Id
    //定义主键生成策略，由数据库自动生成
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
    private LocalDateTime createTime;

    //用户上次登录时间
    private LocalDateTime updateTime;


}
