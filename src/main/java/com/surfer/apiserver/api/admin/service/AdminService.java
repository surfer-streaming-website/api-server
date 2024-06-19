package com.surfer.apiserver.api.admin.service;

import com.surfer.apiserver.api.admin.dto.AdminDTO;
import com.surfer.apiserver.api.admin.dto.AdminDTO.GetArtistApplicationsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.surfer.apiserver.api.admin.dto.AdminDTO.*;

public interface AdminService {
    Page<GetArtistApplicationsResponse> getArtistApplicationsAsPage(Pageable pageable);

    GetArtistApplicationResponse getArtistApplication(Long id);

    void manageArtistApplication(ManageArtistApplicationRequest manageArtistApplicationRequest, Long id);

}
