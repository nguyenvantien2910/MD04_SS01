package com.ra.session01.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {
    String uploadToLocal(MultipartFile multipartFile);
    String uploadToRemote(String localPath);
}
