package com.example.dplus.service.file;


import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessService {

    private final FileService amazonS3Service;

    // S3 내의 파일 업로드, 삭제
    public String uploadImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException ioe) {
            throw new ErrorCustomException(ErrorCode.CONVERTING_FILE_ERROR);
        }
        return amazonS3Service.getFileUrl(fileName);
    }
    public void deleteImage(String url) {
        amazonS3Service.deleteFile(url);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


}
