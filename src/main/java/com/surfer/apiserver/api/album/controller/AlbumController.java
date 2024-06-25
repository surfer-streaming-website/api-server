package com.surfer.apiserver.api.album.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.album.dto.AlbumReq;
import com.surfer.apiserver.api.album.dto.AlbumRes;
import com.surfer.apiserver.api.album.dto.AlbumRes;
import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/album")
@RestController
public class AlbumController {

    private ObjectMapper objectMapper;

    private AlbumService albumService;


    @Autowired
    public AlbumController(ObjectMapper objectMapper, AlbumService albumService) {
        this.objectMapper = objectMapper;
        this.albumService = albumService;

    }
    //마이페이지 신청리스트
    @GetMapping("/status")
    public  ResponseEntity<?> findAllByMemberEntityId() {

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String memberSeq = AES256Util.decrypt(authentication1.getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.valueOf(authentication.getName());
        System.out.println("memberSeq: " + memberSeq);
        System.out.println("memberId: " + memberId);

        return new ResponseEntity<>(albumService.findAllByMemberEntityId(Long.parseLong(memberSeq)), HttpStatus.OK);
    }

    //마이페이지 신청리스트 test
    @GetMapping("/status/{id}")
    public  ResponseEntity<?> findAllByMemberEntityId(@PathVariable Long id) {

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        String memberSeq = AES256Util.decrypt(authentication1.getName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.valueOf(authentication.getName());
        System.out.println("memberSeq: " + memberSeq);
        System.out.println("memberId: " + memberId);

        return new ResponseEntity<>(albumService.findAllByMemberEntityId(id), HttpStatus.OK);
    }

    //마이페이지 앨범 상세보기
    @GetMapping("/{albumSeq}")
    public ResponseEntity<?> findAlbum(@PathVariable Long albumSeq) {
        //앨범정보
        AlbumEntity albumEntity = albumService.findAlbum(albumSeq);

        //앨범 가수 정보
        List<AlbumSingerEntity> albumSingerList = albumService.findAlbumSingerList(albumEntity);

        AlbumRes albumRes = new AlbumRes(albumEntity,albumSingerList);

        return new ResponseEntity<>(albumRes, HttpStatus.OK);
    }

    //앨범 이미지 찾기
    @GetMapping("/image/{albumSeq}")
    public ResponseEntity<?> findAlbumImg(@PathVariable Long albumSeq) {

        //앨범정보
        AlbumEntity albumEntity = albumService.findAlbum(albumSeq);

        if (albumEntity == null) {
            return ResponseEntity.notFound().build();
        }
        URL downloadUrl = albumService.generateAlbumImgFileUrl(albumEntity.getAlbumImage());
        return ResponseEntity.ok(downloadUrl.toString());


    }
    //앨범 등록
    @PostMapping("/save")
    public ResponseEntity<?> saveAlbum(@RequestPart("multipartfiles") List<MultipartFile> multipartFiles,
                                       @RequestPart("album") AlbumReq albumReq) {
        System.out.println("111111111111111111111");




        System.out.println(albumReq.getAlbumTitle());
        Long memberId = albumReq.getMemberId();


        System.out.println("1========================");

        Map<Integer,String> fielNameMap = albumService.uploadFile(multipartFiles,albumReq);
        System.out.println("2========================");

        fielNameMap.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });
        System.out.println("3========================");

        albumService.albumSave(albumReq, fielNameMap,memberId);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }




    //신청한 앨범 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
        System.out.println("여기까지1");

        albumService.deleteAlbum(id);
        System.out.println("여기까지2");

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }



}

