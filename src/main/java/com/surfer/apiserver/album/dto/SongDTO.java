package com.surfer.apiserver.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SongDTO {
    private Long songSeq;
    private String songTitle;
    private Integer songNumber;
    private String lyrics;
    private String genre;
    private Boolean songState;// default
    private String soundSourceName;
    private AlbumEntity albumEntity;
    private String producer;
    private Integer totalPlayedCount;
    private Integer recentlyPlayedCount;

    private List<String> songSingerNameList;


}




