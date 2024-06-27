package com.surfer.apiserver.api.search.dto;

import lombok.Getter;

@Getter
public class LyricsSearchDTO {
    private String albumImage;
    private String songTitle;
    private String songSingerName;
    private String lyrics;


    public LyricsSearchDTO(String albumImage,String songTitle,String songSingerName,String lyrics) {
        this.albumImage = albumImage;
        this.songTitle = songTitle;
        this.songSingerName = songSingerName;
        this.lyrics = lyrics;
    }
}
