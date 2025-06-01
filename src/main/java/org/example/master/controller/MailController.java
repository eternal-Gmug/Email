package org.example.master.controller;

import org.example.master.Entity.Beans.MailBean;
import org.example.master.Entity.MailEntity;
import org.example.master.Entity.UserEntity;
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

    private final UserService userService;
    private final MailService mailService;

    public MailController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    //响应前端发送请求
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailBean mail){
        logger.info("Received send email request: {}", mail);
        try{
            //先判断邮件的发送方与接收方是否存在
            UserEntity fromUser = userService.findByEmail(mail.getFrom())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            UserEntity toUser = userService.findByEmail(mail.getTo())
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));
            //此时处理发送邮件持久化的业务逻辑
            MailEntity PersistenceMail = mailService.sendEmail(fromUser,toUser,mail.getSubject(), mail.getContent());

        }catch (Exception e) {
            logger.error("Error sending email: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }

        return null;
    }

}
