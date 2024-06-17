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
public class AlbumReq {
    private String albumTitle;
    private String agency;
    private String albumContent;
    private String albumImage;
    private String albumState;
    private String memberId;
    private List<SongDTO> songList;
    private List<AlbumSingerDTO> albumSingerList;





}
