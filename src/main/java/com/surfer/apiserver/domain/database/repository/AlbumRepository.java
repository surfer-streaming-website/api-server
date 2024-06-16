package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<AlbumEntity,Long> {
    Optional<AlbumEntity> findById(Long albumSeq);


    @Query("SELECT ae FROM AlbumEntity ae WHERE ae.memberEntity.memberId = :memberId")
    List<AlbumEntity> findAllAlbum(Long memberId);

}
