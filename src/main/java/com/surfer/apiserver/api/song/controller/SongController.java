package com.surfer.apiserver.api.song.controller;

import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;



    //s3 파일 url
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable Long id) {
        SongEntity songEntity = songService.selectById(id);
        if (songEntity == null) {
            return ResponseEntity.notFound().build();
        }
        URL downloadUrl = songService.generateSongFileUrl(songEntity.getSoundSourceName());
        return ResponseEntity.ok(downloadUrl.toString());
    }

}
