package org.example.master.utils;

import java.util.UUID;

public class FileHandle {
    public String generateUniqueFileName(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
    }
}
