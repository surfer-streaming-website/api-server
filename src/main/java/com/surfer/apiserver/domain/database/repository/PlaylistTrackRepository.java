package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistTrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrackEntity, Long> {
}
