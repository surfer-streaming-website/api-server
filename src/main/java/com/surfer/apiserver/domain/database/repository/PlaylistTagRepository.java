package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTagRepository extends JpaRepository<PlaylistTagEntity, Long> {
}
