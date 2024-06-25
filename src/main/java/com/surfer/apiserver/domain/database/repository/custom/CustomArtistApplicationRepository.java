package com.surfer.apiserver.domain.database.repository.custom;

import com.surfer.apiserver.api.admin.dto.AdminDTO;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomArtistApplicationRepository {
    Page<AuthDTO.GetArtistApplicationsResponse> findNotDeleteArtistApplicationByMember(Pageable pageable, MemberEntity member);

    Page<AdminDTO.GetArtistApplicationsResponse> findPendingArtistApplication(Pageable pageable);
}
