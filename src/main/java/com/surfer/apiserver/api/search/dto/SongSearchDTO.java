package com.surfer.apiserver.api.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongSearchDTO {

    private String albumImage;
    private String songTitle;
    private String songSingerName;
    private Long songSeq;

    public SongSearchDTO(String albumImage,String songTitle,String songSingerName , Long songSeq) {
        this.albumImage = albumImage;
        this.songTitle = songTitle;
        this.songSingerName = songSingerName;
        this.songSeq = songSeq;
    }
}
