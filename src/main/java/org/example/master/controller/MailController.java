package org.example.master.controller;

import org.example.master.Entity.Beans.MailBean;
import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
import org.example.master.repository.UserRepository;
import org.example.master.services.AttachmentService;
import org.example.master.services.MailService;
import org.example.master.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails")
//允许跨域资源共享
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    private final UserRepository userRepository;
    private final MailService mailService;
    private final AttachmentService attachmentService;

    public MailController(UserRepository userRepository, MailService mailService, AttachmentService attachmentService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.attachmentService = attachmentService;
    }

    //响应前端发送请求
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailBean mail){
        logger.info("Received send email request: {}", mail);
        try{
            //先判断邮件的发送方与接收方是否存在
            UserEntity fromUser = userRepository.findByEmail(mail.getFrom())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            UserEntity toUser = userRepository.findByEmail(mail.getTo())
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));

            //此时处理发送邮件持久化的业务逻辑
            //还应该处理接收方的收件箱问题


            MailEntity PersistenceMail = mailService.sendEmail(fromUser,toUser,mail.getSubject(), mail.getContent());

            //将附件传出并持久化进数据表
            if(PersistenceMail==null){
                logger.warn("Email without attachments sent failed");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
            }
            //邮件带有附件才持久化进数据表
            if(PersistenceMail.getHasAttachment()>0) {
                attachmentService.saveAttachmentsWithEmail(fromUser.getUserid(), PersistenceMail);
                logger.info("Attachments have saved");
            }

            logger.info("Email sent successfully from {} to {}", mail.getFrom(), mail.getTo());
            return ResponseEntity.ok("Email sent successfully");
        }catch (Exception e) {
            logger.error("Error sending email: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    //响应前端接收请求


}
