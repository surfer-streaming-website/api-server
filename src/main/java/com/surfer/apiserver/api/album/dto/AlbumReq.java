package com.surfer.apiserver.api.album.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    private Date releaseDate;

    private int albumState;
//    private Long memberId;
    private List<SongDTO> songEntities;
    private List<AlbumSingerDTO> albumSingerEntities;




}
