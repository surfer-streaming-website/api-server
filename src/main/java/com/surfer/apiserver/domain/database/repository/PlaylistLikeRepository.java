package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLikeEntity, Long> {
    /**
     * 현재 로그인 중인 사용자가 좋아요를 누른 모든 플레이리스트 조회
     */
    @Query("SELECT pl FROM playlist_like pl WHERE pl.memberEntity = :member")
    List<PlaylistLikeEntity> findByMember(@Param("member") MemberEntity member);

    /**
     * 현재 로그인 중인 사용자가 좋아요를 누른 플레이리스트 상세 조회
     */
    @Query("SELECT pl FROM playlist_like pl WHERE pl.memberEntity = :member AND pl.playlistGroupEntity = :playlistGroup")
    Optional<PlaylistLikeEntity> liked(@Param("member") MemberEntity member, @Param("playlistGroup") PlaylistGroupEntity playlistGroup);
}
