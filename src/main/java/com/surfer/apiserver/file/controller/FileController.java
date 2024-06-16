package com.surfer.apiserver.file.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.file.dto.LogCreateRequest;
import com.surfer.apiserver.file.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/album/save")
@RestController
@RequiredArgsConstructor
public class FileController{

    private final S3Service s3Service;

    private ObjectMapper objectMapper;

    private AlbumService albumService;

    @Autowired
    public FileController(S3Service s3Service,ObjectMapper objectMapper,AlbumService albumService) {
        this.objectMapper = objectMapper;
        this.s3Service = s3Service;
        this.albumService = albumService;

    }
    @PostMapping
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

}