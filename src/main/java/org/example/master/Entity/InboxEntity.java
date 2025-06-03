package org.example.master.Entity;

import java.util.List;

//收件箱的实体类
public class InboxEntity {
      //收件箱的唯一标识
      private Long Inbox_id;

      //收件箱的名称
      private String name;

      //收件箱的隶属者
      private UserEntity user;

      //收件箱内的邮件
      private List<MailEntity> emails;

      // Getters and Setters
      public Long GetInboxId(){
            return Inbox_id;
      }
      public void SetInboxId(Long Inbox_id){
            this.Inbox_id = Inbox_id;
      }
      public String GetName(){
            return name;
      }
      public void SetName(String name){
            this.name = name;
      }
      public UserEntity GetUser(){
            return user;
      }
      public void SetUser(UserEntity user){
            this.user = user;
      }
      public List<MailEntity> GetEmails(){
            return emails;
      }
      public void SetEmails(List<MailEntity> emails){
            this.emails = emails;
      }
}
