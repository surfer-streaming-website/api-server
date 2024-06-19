package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AlbumReplyLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumReplyLikeRepository extends JpaRepository<AlbumReplyLikeEntity, Long> {
    @Query("SELECT a FROM AlbumReplyLikeEntity a WHERE a.memberEntity.memberId = :memberSeq AND a.albumReplyEntity.albumReplySeq = :albumReplySeq")
    AlbumReplyLikeEntity findByMemberAndAlbumReply(Long memberSeq, Long albumReplySeq);

}