package org.example.master.services;

import org.example.master.Entity.UserEntity;
import org.example.master.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    //使用用户仓库数据访问层对象
    private final UserRepository userRepository;

    //设置用户附件存储基路径
    String attachmentBasePath;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //实现用户查找方法接口
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //用户注册业务逻辑
    public UserEntity registerUser(String username, String email, String password){
        return null;
    }

    public UserEntity loginUser(String username, String password) {
        return null;
    }

    //用户删除业务逻辑
    public void deleteUser(Long id) {

    }

    //对密码进行加密
    public String encryptPassword(String password) {
        return null;
    }

    //对注册邮箱格式进行检查
    public boolean checkEmail(String email) {
        return true;
    }

    //判断用户输入密码是否与数据表中的密码相一致
    public boolean checkPassword(String password,String storedPassword){
        return storedPassword.equals(encryptPassword(password));
    }
}
