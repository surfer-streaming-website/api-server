package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.SongTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongTestRepository extends JpaRepository<SongTestEntity, Long> {
}
