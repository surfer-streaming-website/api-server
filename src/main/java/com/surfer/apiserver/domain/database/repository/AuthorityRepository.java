package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    Optional<AuthorityEntity> findByUserId(Long userId);

    Optional<AuthorityEntity> findByRefreshToken(String refreshToken);
}
