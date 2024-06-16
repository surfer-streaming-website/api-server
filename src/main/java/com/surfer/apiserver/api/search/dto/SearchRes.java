package com.surfer.apiserver.api.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchRes {

    private List<SongSearchDTO> song;
    private List<AlbumSearchDTO> album;
    private List<PlayListSearchDTO> playList;
    private List<LyricsSearchDTO> lyrics;


    public SearchRes(List<SongSearchDTO> song, List<AlbumSearchDTO> album, List<PlayListSearchDTO> playList, List<LyricsSearchDTO> lyrics) {
        this.song = song;
        this.album = album;
        this.playList = playList;
        this.lyrics = lyrics;

    }


//    public SearchRes(List<SearchDTO> song, List<SearchDTO> album, List<SearchDTO> lyrics) {
//        this.song = song;
//        this.album = album;
//        this.lyrics = lyrics;
//
//    }


}
