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

    //Constructors
    public MailBean(){};
    public MailBean(String from,String to,String subject,String content){
        this.from=from;
        this.to=to;
        this.subject=subject;
        this.content=content;
    }

    // Getters and Setters
    public String getFrom(){
        return from;
    }
    public void setFrom(String from){
        this.from = from;
    }
    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to = to;
    }
    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

}
