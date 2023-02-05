package com.example.admin.service;

import com.example.admin.repository.FileRepository;
import com.example.admin.storage.StorageProperties;
import com.example.admin.storage.StorageService;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileService implements StorageService {

    @Autowired
    private FileRepository repository;

    private final MinioClient minioClient;
    private final long minioFileSize;

    @Autowired
    public FileService(StorageProperties properties) {
        minioFileSize = properties.getFileSize();
        minioClient =
                MinioClient.builder()
                        .endpoint("http://127.0.0.1:9000")
                        .credentials("LegZkIjGKmVJ1c07", "xoDMe9hY0wA3ybPAVqL1JxTMJI8AtqmY")
                        .build();
    }

    public boolean bucketExists(String bucketName) {
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                System.out.println(bucketName + " 不存在");
            }
            return found;
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        }
        return false;
    }

    public String getUrl(String bucketName, String objectName) {
       try {
           boolean flag = bucketExists(bucketName);
           String url = "";
           if (flag) {
               url = minioClient.getPresignedObjectUrl(
                       GetPresignedObjectUrlArgs.builder()
                               .method(Method.GET)
                               .bucket(bucketName)
                               .object(objectName)
                               .expiry(2, TimeUnit.MINUTES)
                               .build());
           }
           return url;
       } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
           System.out.println("Error occurred: " + e);
       }

        return "";
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            boolean found = bucketExists("admin");
            if (found) {
                InputStream inputStream = new ByteArrayInputStream(file.getBytes());

                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = localDateTime.format(formatter);

                minioClient.putObject(PutObjectArgs.builder()
                        .bucket("admin")
                        .object(formattedDate + "/" + UUID.randomUUID() + "/" + file.getOriginalFilename())
                        .stream(inputStream,-1, minioFileSize)
                        .contentType(file.getContentType())
                        .build()
                );
                return getUrl("admin", file.getOriginalFilename());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        }

        return "";
    }

}