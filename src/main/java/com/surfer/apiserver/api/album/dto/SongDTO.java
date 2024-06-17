package com.surfer.apiserver.api.album.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private String SongTitle;
    private int songNumber;
    private String lyrics;
    private int totalPlayedCount;
    private int recentlyPlayedCount;
    private String genre;
    private boolean songState;
    private String soundSourceName;
    private String producer;
    private String albumSeq;
    private List<SongSingerDTO> songSingerList;



}
