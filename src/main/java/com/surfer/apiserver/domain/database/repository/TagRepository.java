package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
