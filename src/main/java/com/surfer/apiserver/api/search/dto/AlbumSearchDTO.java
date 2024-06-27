package com.surfer.apiserver.api.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumSearchDTO {

    private String albumImage;
    private String albumTitle;
    private String albumSingerName;
    private Long albumSeq;
    private int albumState;

    //앨범 2
    public AlbumSearchDTO(String albumImage,String albumTitle,String albumSingerName,Long albumSeq,int albumState) {
        this.albumImage =albumImage;
        this.albumTitle = albumTitle;
        this.albumSingerName = albumSingerName;
        this.albumSeq = albumSeq;
        this.albumState = albumState;
    }
}
