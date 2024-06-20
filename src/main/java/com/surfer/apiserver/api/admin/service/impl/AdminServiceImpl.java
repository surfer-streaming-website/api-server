package com.surfer.apiserver.api.admin.service.impl;

import com.surfer.apiserver.api.admin.dto.AdminDTO;
import com.surfer.apiserver.api.admin.service.AdminService;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.ArtistApplicationRepository;
import com.surfer.apiserver.domain.database.repository.MemberAuthorityRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.custom.CustomArtistApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.surfer.apiserver.api.admin.dto.AdminDTO.GetArtistApplicationsResponse;
import static com.surfer.apiserver.api.admin.dto.AdminDTO.ManageArtistApplicationRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final ArtistApplicationRepository artistApplicationRepository;
    private final CustomArtistApplicationRepository customArtistApplicationRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;

    @Override
    public Page<GetArtistApplicationsResponse> getArtistApplicationsAsPage(Pageable pageable) {
        return customArtistApplicationRepository.findPendingArtistApplication(pageable);
    }

    @Override
    public AdminDTO.GetArtistApplicationResponse getArtistApplication(Long id) {
        return AdminDTO.GetArtistApplicationResponse
                .convertByArtistApplicationEntity(artistApplicationRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND)));
    }

    @Override
    @Transactional
    public void manageArtistApplication(ManageArtistApplicationRequest manageArtistApplicationRequest, Long id) {
        ArtistApplicationEntity artistApplicationEntity = artistApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
        artistApplicationEntity.setStatus(CommonCode.ArtistApplicationStatus.fromString(manageArtistApplicationRequest.getStatus()));
        artistApplicationRepository.save(artistApplicationEntity);
        if (CommonCode.ArtistApplicationStatus.fromString(manageArtistApplicationRequest.getStatus())
                .equals(CommonCode.ArtistApplicationStatus.Completed)) {
            memberAuthorityRepository.save(MemberAuthorityEntity.builder()
                    .member(artistApplicationEntity.getMember())
                    .authority(CommonCode.MemberAuthority.ROLE_SINGER)
                    .build());
        }
    }

}
