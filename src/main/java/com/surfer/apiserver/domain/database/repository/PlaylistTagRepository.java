package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistTagEntity;
import com.surfer.apiserver.domain.database.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistTagRepository extends JpaRepository<PlaylistTagEntity, Long> {
    /**
     * playlistGroupEntity 에 해당하는 playlistTag 찾기
     */
    @Query("SELECT pt FROM playlist_tag pt WHERE pt.playlistGroupEntity = :groupEntity")
    List<PlaylistTagEntity> findByPlaylistGroup(@Param("groupEntity") PlaylistGroupEntity playlistGroup);
}
