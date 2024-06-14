package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.api.song.dto.SongReplyRes;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SongReplyRepository extends JpaRepository<SongReplyEntity, Long> {
    @Query(value = "select new com.surfer.apiserver.api.song.dto.SongReplyRes(sr) from SongReplyEntity sr join fetch sr.memberEntity where sr.songEntity = :song",
            countQuery = "select count(distinct sr.songReplySeq) from SongReplyEntity sr where sr.songEntity = :song")
    Page<SongReplyRes> findAllBySong(SongEntity song, Pageable pageable);
}
