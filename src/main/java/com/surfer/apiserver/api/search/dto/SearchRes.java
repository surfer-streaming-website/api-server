package com.surfer.apiserver.api.search.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchRes {

    private List<SongSearchDTO> song;
    private List<AlbumSearchDTO> album;
    private List<PlaylistSearchDTO> playlist;
    private List<LyricsSearchDTO> lyrics;


    public SearchRes(List<SongSearchDTO> song, List<AlbumSearchDTO> album, List<PlaylistSearchDTO> playlist, List<LyricsSearchDTO> lyrics) {
        this.song = song;
        this.album = album;
        this.playlist = playlist;
        this.lyrics = lyrics;

    }


//    public SearchRes(List<SearchDTO> song, List<SearchDTO> album, List<SearchDTO> lyrics) {
//        this.song = song;
//        this.album = album;
//        this.lyrics = lyrics;
//
//    }


}
