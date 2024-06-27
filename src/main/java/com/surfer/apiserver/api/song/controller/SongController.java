package com.surfer.apiserver.api.song.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    private SongService  songService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private MemberRepository memberRepository;

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


    @GetMapping("/{songId}/like-status")
    public ResponseEntity<RestApiResponse> isLiked(@PathVariable Long songId) {
        boolean isLiked = songService.isSongLikedByUser(songId);
        return ResponseEntity.ok(new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS), isLiked));
    }

    @PostMapping("/{songId}/like")
    public ResponseEntity<RestApiResponse> likeSong(@PathVariable Long songId) {
        songService.likeSong(songId);
        return ResponseEntity.ok(new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS)));
    }

    @DeleteMapping("/{songId}/like")
    public ResponseEntity<RestApiResponse> unlikeSong(@PathVariable Long songId) {
        songService.unlikeSong(songId);
        return ResponseEntity.ok(new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS)));
    }

    @GetMapping("/{songId}/like-count")
    public ResponseEntity<RestApiResponse> likeCount(@PathVariable Long songId) {
        long count = songService.countSongLikes(songId);
        return ResponseEntity.ok(new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS), count));
    }


}
