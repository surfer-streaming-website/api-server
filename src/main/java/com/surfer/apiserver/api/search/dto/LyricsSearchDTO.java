package com.surfer.apiserver.api.search.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LyricsSearchDTO {
    private String albumImage;
    private String songTitle;
    private String songSingerName;
    private String lyrics;
    private Long songSeq;
    private int albumState;


    public LyricsSearchDTO(String albumImage,String songTitle,String songSingerName,String lyrics, Long songSeq,int albumState) {
        this.albumImage = albumImage;
        this.songTitle = songTitle;
        this.songSingerName = songSingerName;
        this.lyrics = lyrics;
        this.songSeq = songSeq;
        this.albumState = albumState;
    }
}
