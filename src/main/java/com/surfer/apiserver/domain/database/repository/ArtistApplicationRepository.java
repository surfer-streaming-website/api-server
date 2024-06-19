package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArtistApplicationRepository extends JpaRepository<ArtistApplicationEntity, Long> {
    Optional<ArtistApplicationEntity> findByMember(MemberEntity memberEntity);
}
