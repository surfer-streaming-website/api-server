package com.surfer.apiserver.album.controller;

import com.surfer.apiserver.album.dto.AlbumReq;
import com.surfer.apiserver.album.service.AlbumService;
import com.surfer.apiserver.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/album")
public class AlbumController {



    @Autowired
    private AlbumService albumService;


    /*
    * 앨범 및 음악 등록
    * */
//    @PostMapping("/save")
//    public String save(SongEntity songEntity) {
//        songService.saveSong(songEntity);
//        System.out.println("sava ok!!!");
//        return "save ok";
//
//    }


    @PostMapping("/save")
    public ResponseEntity<?> albumSave(@RequestBody AlbumReq albumReq ) {

        albumService.saveAlbum(albumReq);

        System.out.println("save ok!!!");
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");


    }

   /* @PostMapping("/save1")
    public ResponseEntity<?> albumSave1(@RequestBody AlbumReq1 albumReq1) {

        albumService.saveAlbum1(albumReq1);

        System.out.println("sava1 ok!!!");
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");


    }*/

}
