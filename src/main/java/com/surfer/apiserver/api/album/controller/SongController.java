/*
package com.surfer.apiserver.api.album.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.file.service.FileStorageService;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.domain.database.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.findById(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Song createSong(@RequestPart("song") String songJson, @RequestPart("file") MultipartFile file) throws Exception {
        String fileName = fileStorageService.storeFile(file, false); // false indicates song file
        Song song = objectMapper.readValue(songJson, Song.class);
        song.setSoundSourceName(fileName);
        return songService.save(song);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Song updateSong(@PathVariable Long id, @RequestPart("song") String songJson, @RequestPart("file") MultipartFile file) throws Exception {
        Song existingSong = songService.findById(id);
        if (existingSong != null) {
            String fileName = fileStorageService.storeFile(file, false); // false indicates song file
            Song song = objectMapper.readValue(songJson, Song.class);
            song.setSoundSourceName(fileName);
            song.setSeq(id);
            return songService.save(song);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable Long id) {
        Song song = songService.findById(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        URL downloadUrl = fileStorageService.generateFileDownloadUrl(song.getSoundSourceName());
        return ResponseEntity.ok(downloadUrl.toString());
    }
}
*/
