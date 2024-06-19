import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;/*
package com.surfer.apiserver.api.song.service.impl;

<FileStorgeService.java>

package com.surfer.apiserver.api.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileStorageService {

    private final AmazonS3 s3Client;
    private final String bucketName;
    private final String imageDir;
    private final String songDir;

    public FileStorageService(AmazonS3 s3Client,
                              @Value("${cloud.aws.s3.bucket}") String bucketName,
                              @Value("${file.image-dir}") String imageDir,
                              @Value("${file.song-dir}") String songDir) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.imageDir = imageDir;
        this.songDir = songDir;
    }

    public String storeFile(MultipartFile file, boolean isImage) {
        String fileDir = isImage ? imageDir : songDir;
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = fileDir + "/" + fileName;

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file.getInputStream(), null);
            s3Client.putObject(putObjectRequest);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }

        return filePath;
    }

    public URL generateFileDownloadUrl(String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(com.amazonaws.HttpMethod.GET)
                        .withExpiration(expiration);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(s3Client.getUrl(bucketName, fileName).toURI());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        } catch (Exception ex) {
            throw new RuntimeException("Could not load file " + fileName, ex);
        }
    }
}
*/
