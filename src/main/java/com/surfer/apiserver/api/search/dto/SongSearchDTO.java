package com.surfer.apiserver.api.search.dto;

import lombok.Getter;

@Getter
public class SongSearchDTO {

    private String albumImage;
    private String songTitle;
    private String songSingerName;

    public SongSearchDTO(String albumImage,String songTitle,String songSingerName) {
        this.albumImage = albumImage;
        this.songTitle = songTitle;
        this.songSingerName = songSingerName;

    }
}
