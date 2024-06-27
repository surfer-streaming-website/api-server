package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
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
    private int albumState;
    private Long memberId;
    private List<SongDTO> songEntities;
    private List<AlbumSingerDTO> albumSingerEntities;




}
