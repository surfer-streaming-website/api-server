package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthorityEntity, Long> {
}