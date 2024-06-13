package com.surfer.apiserver.api.playlist.service;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;

public interface PlaylistService {
    /**
     * 플레이리스트 생성
     */
    int createNewPlaylist(PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO);
}
