package org.example.master.controller;

import org.example.master.Entity.AttachmentEntity;
import org.example.master.Entity.UserEntity;
import org.example.master.repository.AttachmentRepository;
import org.example.master.repository.UserRepository;
import org.example.master.services.AttachmentService;
import org.example.master.utils.FileHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private FileHandle fileHandle;

    public AttachmentController(UserRepository userRepository, AttachmentRepository attachmentRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
    }

    //处理附件上传请求（仅用于写邮件中的上传附件）
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("userId") Long userId){
        //根据用户的ID值查找用户是否存在
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()){
            logger.warn("User not found");
            return ResponseEntity.badRequest().body("User not found");
        }

        //将查找到的用户转为用户实体类
        UserEntity user = userOpt.get();

        if(file.isEmpty()){
            logger.warn("File not found");
            return ResponseEntity.badRequest().body("No file selected");
        }

        String fileName = file.getOriginalFilename();
        //使用用户的附件下载文件夹路径保存文件
        String userAttachmentFolderPath = user.getAttachmentFolderPath();
        Path filepath = Paths.get(userAttachmentFolderPath, fileName);

        try{
            if(!Files.exists(filepath.getParent())){
                Files.createDirectories(filepath.getParent());
            }
            //防止文件覆盖
            //File对象可以用于检查文件是否存在、创建文件等操作
            File attachmentFile = filepath.toFile();
            if (attachmentFile.exists()) {
                //生成文件名后缀，避免同名文件
                String FileName = fileHandle.generateUniqueFileName(fileName);
                filepath = Paths.get(userAttachmentFolderPath, FileName);
                attachmentFile = filepath.toFile();
            }
            //将文件写入存放路径
            file.transferTo(attachmentFile);

            //为附件生成一个随机标识号作为单封邮件的附件标识号
            String TempIdentification = UUID.randomUUID().toString();
            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setUser(user);
            attachment.setFilename(fileName);
            attachment.setFilepath(filepath.toString());
            attachment.setUploadTime(LocalDateTime.now());
            //设置Map结构返回一个暂时标识符和附件实体
            Map<String,AttachmentEntity> response = new LinkedHashMap<>();
            response.put(TempIdentification, attachment);
            //将邮件中上传的文件先保存进静态附件文件夹中，以保证在发送邮件时能将附件文件夹中的附件一并发出，缺点（只允许一台客户端访问，后续优化）
            AttachmentService.attachmentTempFolder.put(TempIdentification,attachment);
            logger.info("Uploaded file: {} successfully", fileName);
            return ResponseEntity.status(200).body(response);

        }catch(IOException e){
            logger.error("Failed to upload file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to upload file");
        }
    }

    //响应附件删除请求，这里的删除附件表示在一封邮件中删除上传的附件
    @PostMapping("delete")
    public ResponseEntity<?> deleteAttachment(@RequestParam("virtual_id") String virtualId){
        try {
            AttachmentService.attachmentTempFolder.remove(virtualId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            logger.error("Failed to delete file: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to delete file");
        }
    }

    //响应下载附件请求
    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long attachmentId) {
        Optional<AttachmentEntity> attachmentOpt = attachmentRepository.findById(attachmentId);
        if (attachmentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AttachmentEntity attachment = attachmentOpt.get();
        Path filePath = Paths.get(attachment.getFilepath());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String originalFileName = attachment.getFilename();
            //使用 URLEncoder.encode 对文件名进行 URL 编码，确保文件名中的特殊字符（如空格、中文等）不会导致 HTTP 响应头解析错误。
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());

            logger.info("Attachment downloaded successfully");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)      //MIME类型，表示任意二进制数据
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("Failed to download file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
