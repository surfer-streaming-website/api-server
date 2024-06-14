package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<AlbumEntity,Long> {
    Optional<AlbumEntity> findById(Long albumSeq);
}
