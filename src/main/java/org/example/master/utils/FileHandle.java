package org.example.master.utils;

import java.util.UUID;

//文件格式工具类
public class FileHandle {
    public String generateUniqueFileName(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
    }
}
