package org.example.master.Entity.Beans;

import java.time.LocalDateTime;
import java.util.List;

//前端邮件格式的对应字段类，为了反序化Json字段
public class MailBean {
    //邮件的发送方邮箱名
    private String from;
    //邮件的接收方邮箱名
    private String to;
    //邮件的主题
    private String subject;
    //邮件的正文
    private String content;

}
