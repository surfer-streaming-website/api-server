package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AlbumReplyLikeEntity;
import com.surfer.apiserver.domain.database.entity.SongReplyLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SongReplyLikeRepository extends JpaRepository<SongReplyLikeEntity, Long> {
    @Query("SELECT a FROM SongReplyLikeEntity a WHERE a.memberEntity.memberId = :memberSeq AND a.songReplyEntity.songReplySeq = :songReplySeq")
    SongReplyLikeEntity findByMemberAndSongReply(Long memberSeq, Long songReplySeq);
}
