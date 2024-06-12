package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumSingerRepository extends JpaRepository<AlbumSingerEntity, Long> {
    @Query(value = "select ase from AlbumSingerEntity ase where ase.albumEntity = :album")
    List<AlbumSingerEntity> findAllByAlbum(AlbumEntity album);
}
