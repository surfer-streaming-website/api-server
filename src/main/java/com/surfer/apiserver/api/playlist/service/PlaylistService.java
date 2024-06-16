package com.surfer.apiserver.api.playlist.service;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.domain.database.entity.PlaylistLikeEntity;
import com.surfer.apiserver.domain.database.entity.TagEntity;

import java.util.List;

public interface PlaylistService {
    /**
     * 플레이리스트 생성
     */
    int createNewPlaylist(PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO, Long songSeq);

    /**
     * 플레이리스트에 노래 추가
     */
    int insertSongIntoPlaylist(Long songSeq, Long playlistGroupSeq);

    /**
     * 현재 로그인 중인 사용자가 가진 모든 플레이리스트 조회
     */
    List<PlaylistDTO.PlaylistGroupResponseDTO> getAllPlaylists();

    /**
     * 해당 플레이리스트의 세부 정보
     *  - 플레이리스트 이름, 플레이리스트 소유자, 플레이리스트 태그, 플레이리스트의 공개여부, 노래들
     */
    PlaylistDTO.PlaylistGroupResponseDTO getPlaylistById(Long playlistGroupSeq);

    /**
     * 플레이리스트 이름, 공개여부 변경, 태그 추가/수정
     */
    int changePlaylist(Long playlistGroupSeq, PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO);

    /**
     * 플레이리스트의 태그 삭제
     */
    int deleteTag(Long playlistGroupSeq, TagEntity tagEntity);

    /**
     * 플레이리스트 삭제
     */
    int deletePlaylistById(Long playlistGroupSeq);

    /**
     * 플레이리스트에서 노래 삭제
     */
    int deleteSongFromPlaylistById(Long playlistGroupSeq, Long songSeq);

    /**
     * 플레이리스트에 좋아요가 존재하는지 체크하고 있다면 전부 반환
     */
    List<PlaylistDTO.PlaylistLikeResponseDTO> likedPlaylists();

    /**
     * 해당 플레이리스트에 좋아요 추가
     */
    int insertPlaylistLikeById(Long playlistGroupSeq);

    /**
     * 해당 플레이리스트에서 좋아요 삭제
     */
    int deletePlaylistLikeById(Long playlistGroupSeq);
}
