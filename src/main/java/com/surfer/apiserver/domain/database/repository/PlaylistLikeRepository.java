package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.PlaylistLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLikeEntity, Long> {
}
