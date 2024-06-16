package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistTagEntity;
import com.surfer.apiserver.domain.database.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlaylistTagRepository extends JpaRepository<PlaylistTagEntity, Long> {
    /**
     * playlistGroupEntity 와 tagEntity 를 기준으로 하여 해당하는 playlistTag 찾기
     */
    @Query("SELECT pt FROM playlist_tag pt WHERE pt.playlistGroupEntity = :groupEntity AND pt.tagEntity = :tagEntity")
    Optional<PlaylistTagEntity> findByPlaylistGroupAndTag(@Param("groupEntity") PlaylistGroupEntity playlistGroup, @Param("tagEntity")TagEntity tag);

    /**
     * playlistGroupEntity 에 해당하는 playlistTag 찾기
     */
    @Query("SELECT pt FROM playlist_tag pt WHERE pt.playlistGroupEntity = :groupEntity")
    Optional<PlaylistTagEntity> findByPlaylistGroup(@Param("groupEntity") PlaylistGroupEntity playlistGroup);
}
