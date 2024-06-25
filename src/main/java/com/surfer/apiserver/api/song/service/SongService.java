package com.surfer.apiserver.api.song.service;

import com.surfer.apiserver.domain.database.entity.SongEntity;

import java.net.URL;

public interface SongService {

    // 노래 정보 호출
    SongEntity selectById(Long seq);

    // 노래 url 찾기
    URL generateSongFileUrl(String fileName);

    // 좋아요 기능 추가
    boolean isSongLikedByUser(Long songId, Long memberId);
    void likeSong(Long songId, Long memberId);
    void unlikeSong(Long songId, Long memberId);
    long countSongLikes(Long songId);
}
