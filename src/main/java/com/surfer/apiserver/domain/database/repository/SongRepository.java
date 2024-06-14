package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<SongEntity, Long> {

}
