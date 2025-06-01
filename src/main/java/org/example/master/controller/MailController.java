package org.example.master.controller;

import org.example.master.Entity.Beans.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails")
//允许跨域资源共享
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    //响应前端发送请求
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody MailBean mail){
        logger.info("Received send email request: {}", mail);

        return null;
    }

}
