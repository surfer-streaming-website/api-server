package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByRefreshToken(String refreshToken);
}
