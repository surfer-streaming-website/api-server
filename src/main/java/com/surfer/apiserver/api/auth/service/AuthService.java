package com.surfer.apiserver.api.auth.service;

import com.surfer.apiserver.common.jwt.JwtTokenProvider.TokenInfo;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

public interface AuthService {
    void signUp(SignUpRequest signUpRequest);

    TokenInfo signInByEmailAndPassword(SignInRequest signInRequest);

    TokenInfo signInByRefreshToken(String refreshToken);

    void createArtistApplication(CreateArtistApplicationRequest createArtistApplication);

    Page<GetArtistApplicationsResponse> getArtistApplicationsAsPage(Pageable pageable);

    GetArtistApplicationResponse getArtistApplication(Long id);

    void updateArtistApplication(UpdateArtistApplicationRequest updateArtistApplicationRequest);

    void deleteArtistApplication(Long artistApplicationId);

    //    Page<UserResponse> getUsers(Pageable pageable);
}
