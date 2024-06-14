package com.surfer.apiserver.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumReq {
        private List<SongDTO> songDTOList;
        private AlbumDTO albumDTO;


    }
