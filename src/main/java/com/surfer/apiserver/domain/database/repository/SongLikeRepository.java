package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongLikeEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongLikeRepository extends JpaRepository<SongLikeEntity, Long> {
    Optional<SongLikeEntity> findBySongAndMember(SongEntity song, MemberEntity member);
    long countBySong(SongEntity song);
}
