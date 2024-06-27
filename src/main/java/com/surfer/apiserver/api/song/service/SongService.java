package com.surfer.apiserver.api.song.service;

import com.surfer.apiserver.api.song.dto.ResponseSongByGenreDTO;
import com.surfer.apiserver.domain.database.entity.SongEntity;

import java.net.URL;
import java.util.List;

public interface SongService {

    // 노래 정보 호출
    SongEntity selectById(Long seq);

    // 노래 url 찾기
    URL generateSongFileUrl(String fileName);

    // 좋아요 기능 추가
    boolean isSongLikedByUser(Long songId);
    void likeSong(Long songId);
    void unlikeSong(Long songId);
    long countSongLikes(Long songId);

    // 장르별 음악 조회 및 전체 음악 조회
//    List<SongEntity> getSongsByGenre(String genre);
    List<SongEntity> getAllSongs();
    ResponseSongByGenreDTO getSongs();
    ResponseSongByGenreDTO getSongsByGenre(String genre);
}
