package org.example.master.controller;

import org.example.master.Entity.UserEntity;
import org.example.master.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //定义了一个静态的、不可变的日志记录器，用于在UserController类中记录日志信息
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserEntity userEntity){
        UserEntity registeredUser = userService.registerUser(userEntity.getUsername(),userEntity.getEmail(),userEntity.getPassword());
        if(registeredUser != null){
            logger.info("Registered successful for email: {}", userEntity.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        }else{
            logger.warn("Registered failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid registered email");
        }
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody UserEntity userEntity){
        logger.info("Login attempt for email: {}", userEntity.getEmail());
        UserEntity loginUser = userService.loginUser(userEntity.getUsername(), userEntity.getPassword());
        if(loginUser != null){
            logger.info("Login successful for email: {}", userEntity.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(loginUser);
        }else{
            logger.warn("Login failed");
            //这里可以修改一下，明确登录失败的原因
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Wrong password");
        }
    }

    //删除用户响应接口
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
