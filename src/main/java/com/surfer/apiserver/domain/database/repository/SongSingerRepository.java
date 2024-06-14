package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongSingerRepository extends JpaRepository<SongSingerEntity, Long> {
    @Query(value = "select ss from SongSingerEntity ss where ss.songEntity = :song")
    List<SongSingerEntity> findAllBySong(SongEntity song);
}
