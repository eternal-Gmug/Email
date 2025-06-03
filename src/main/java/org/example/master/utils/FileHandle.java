package org.example.master.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
//文件格式工具类
public class FileHandle {
    //生成当前时间戳字符串
    public static String generateTimestamp() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式（年月日时分秒）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");
        // 格式化时间
        return now.format(formatter);
    }

    //用于生成重名文件后缀码
    public String generateUniqueFileName(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return fileName+" "+generateTimestamp()+ extension;
    }
}
