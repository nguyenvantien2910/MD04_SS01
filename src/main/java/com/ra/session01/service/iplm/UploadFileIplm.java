package com.ra.session01.service.iplm;

import com.google.cloud.storage.*;
import com.ra.session01.service.UploadFile;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileIplm implements UploadFile {
    @Autowired
    private ServletContext servletContext;

    @Value("${bucket_name}")
    private String bucketName;

    @Autowired
    private Storage storage;

    @Override
    public String uploadToLocal(MultipartFile multipartFile) {
        String path = servletContext.getRealPath("/uploads");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        String localPath = file.getAbsolutePath() + File.separator + fileName;
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(localPath));
            return uploadToRemote(localPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadToRemote(String localPath) {
        Path path = Paths.get(localPath);
        String fileName = path.getFileName().toString();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        List<Acl> aclList = new ArrayList<>();
        aclList.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        blobInfo = blobInfo.toBuilder().setAcl(aclList).build();

        try {
            Blob blob = storage.create(blobInfo, Files.readAllBytes(path));
            return blob.getMediaLink();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
