package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrackEntity, Long> {
    /**
     * playlistGroupSeq 와 songSeq 을 기준으로 일치하는 playlistTrack 조회
     */
    @Query("SELECT pt FROM playlist_track pt WHERE pt.playlistGroupEntity = :playlist AND pt.songEntity = :song")
    Optional<PlaylistTrackEntity> findByPlaylistGroupSeqAndSongSeq(@Param("playlist") PlaylistGroupEntity playlist, @Param("song") SongEntity song);

    /**
     * playlistGroupSeq 를 기준으로 해당하는 playlistTrack 전부 조회
     */
    @Query("SELECT pt FROM playlist_track pt WHERE pt.playlistGroupEntity = :playlist ORDER BY pt.regDate")
    List<PlaylistTrackEntity> findByPlaylistGroupSeq(@Param("playlist") PlaylistGroupEntity playlist);
}
