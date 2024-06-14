package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.api.album.dto.AlbumReplyRes;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumReplyRepository extends JpaRepository<AlbumReplyEntity,Long> {
    @Query(value = "select new com.surfer.apiserver.api.album.dto.AlbumReplyRes(ar) from AlbumReplyEntity ar join fetch ar.memberEntity where ar.albumEntity = :album",
        countQuery = "select count(distinct ar.albumReplySeq) from AlbumReplyEntity ar where ar.albumEntity = :album")
    Page<AlbumReplyRes> findAllByAlbum(AlbumEntity album, Pageable pageable);
}
