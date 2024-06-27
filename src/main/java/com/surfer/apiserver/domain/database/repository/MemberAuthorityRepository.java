package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthorityEntity, Long> {
}
