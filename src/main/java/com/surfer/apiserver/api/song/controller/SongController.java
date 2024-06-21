package com.surfer.apiserver.api.song.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;

    //음악 파일 url
    @GetMapping("/file/{id}")
    public ResponseEntity<String> findSongUrl(@PathVariable Long id) {
        SongEntity songEntity = songService.selectById(id);
        if (songEntity == null) {
            return ResponseEntity.notFound().build();
        }
        URL downloadUrl = songService.generateSongFileUrl(songEntity.getSoundSourceName());
        return ResponseEntity.ok(downloadUrl.toString());
    }


    //음악 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id, HttpServletRequest request) {

        SongEntity songEntity = songService.selectById(id);
        if (songEntity == null) {
            return ResponseEntity.notFound().build();
        }
        Map<Integer,Object> map = songService.songFileDownload(id,request);
        //S3ObjectInputStream objectInputStream = songService.songFileDownload(id,request);


        String downloadFileName = songEntity.getSoundSourceName();

        S3ObjectInputStream objectInputStream = (S3ObjectInputStream) map.get(1);
        HttpHeaders headers = (HttpHeaders) map.get(2);
        return new ResponseEntity<>(new InputStreamResource(objectInputStream), headers, HttpStatus.OK);

    }

}
