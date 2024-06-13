package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistGroupRepository extends JpaRepository<PlaylistGroupEntity, Long> {
}
