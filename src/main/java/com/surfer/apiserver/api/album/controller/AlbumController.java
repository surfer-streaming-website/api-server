package com.surfer.apiserver.api.album.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.album.dto.AlbumRes;
import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.api.song.service.SongBoardService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.file.dto.LogCreateRequest;
import com.surfer.apiserver.file.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/album")
@RestController
public class AlbumController {

    private final S3Service s3Service;

    private ObjectMapper objectMapper;

    private AlbumService albumService;


    @Autowired
    public AlbumController(S3Service s3Service,ObjectMapper objectMapper,AlbumService albumService) {
        this.objectMapper = objectMapper;
        this.s3Service = s3Service;
        this.albumService = albumService;

    }

    //마이페이지 신청리스트
    @GetMapping("/status")
    public  ResponseEntity<?> findAllByMemberEntityId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.valueOf(authentication.getName());

        return new ResponseEntity<>(albumService.findAllByMemberEntityId(memberId), HttpStatus.OK);
    }

    //마이페이지 신청리스트 test
    @GetMapping("/status/{id}")
    public  ResponseEntity<?> findAllByMemberEntityId(@PathVariable Long id) {

        return new ResponseEntity<>(albumService.findAllByMemberEntityId(id), HttpStatus.OK);
    }



    /*  @PostMapping("")
    public String saveAlbum(@Valid @ModelAttribute LogCreateRequest request,
                            @RequestPart("album") String albumEntity)  throws Exception  {

        // 파일 있는지 검사
        if (request.getFiles() == null) {
//            throw new BusinessException(ErrorCode.EMPTY_FILES);
        }


        AlbumEntity album = objectMapper.readValue(albumEntity, AlbumEntity.class);
        //return albumService.save(album);

        List<String> files = s3Service.uploadFile(request.getFiles());
        albumService.saveAlbum(album);
        System.out.println("Files: " + files);
        System.out.println("album: " + album);

        //return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_LOG_SUCCESS, ""));
        return "ok";
    }
*/


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

    //신청한 앨범 삭제
    @DeleteMapping("/delete/album")
    public ResponseEntity<?> deleteAlbum(@RequestParam Long albumSeq) {

        albumService.deleteAlbum(albumSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }





/*    @PutMapping("/update/{albumSeq}")
        public ResponseEntity<?> updateAlbum(@PathVariable int albumSeq){



        return new ResponseEntity<>(albumService.findByIdAlbum(albumSeq), HttpStatus.OK);

        }*/




    }

