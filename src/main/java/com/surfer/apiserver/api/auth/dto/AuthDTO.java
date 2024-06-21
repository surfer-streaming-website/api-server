package com.surfer.apiserver.api.auth.dto;

import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.surfer.apiserver.common.constant.CommonCode.*;

public class AuthDTO {
    @Data
    @Builder
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class SignUpRequest {
        @NotEmpty(message = "email 필수값입니다.")
        @NotBlank(message = "email 공백을 허용하지 않습니다.")
        private String email;
        @NotEmpty(message = "password 필수값입니다.")
        @NotBlank(message = "password 공백을 허용하지 않습니다.")
        private String password;
        @NotEmpty(message = "nickname 필수값입니다.")
        private String nickname;
        @NotEmpty(message = "name 필수값입니다.")
        private String name;
    }

    @Data
    public static class SignInRequest {
        @NotBlank(message = "email 필수값입니다.")
        private String email;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
    }

    @Data
    public static class CreateArtistApplicationRequest {
        @NotBlank(message = "국내외 구분 필수값입니다.")
        private String locationType;
        @NotBlank(message = "부문 필수값입니다.")
        private String sector;
        @NotBlank(message = "저작물명 필수값입니다.")
        private String copyrightName;
        private String albumName;
        @NotBlank(message = "가수명 필수값입니다.")
        private String artistName;
        @NotBlank(message = "저작자명 필수값입니다.")
        private String authorName;
    }

    @Data
    public static class UpdateArtistApplicationRequest {
        private Long artistApplicationId;
        @NotBlank(message = "국내외 구분 필수값입니다.")
        private String locationType;
        @NotBlank(message = "부문 필수값입니다.")
        private String sector;
        @NotBlank(message = "저작물명 필수값입니다.")
        private String copyrightName;
        private String albumName;
        @NotBlank(message = "가수명 필수값입니다.")
        private String artistName;
        @NotBlank(message = "저작자명 필수값입니다.")
        private String authorName;
    }
    @Data
    @Builder
    public static class GetArtistApplicationResponse {
        private Long artistApplicationId;
        private String locationType;
        private String sector;
        private String copyrightName;
        private String albumName;
        private String artistName;
        private String authorName;
        private String status;

        public static GetArtistApplicationResponse convertByArtistApplicationEntity(ArtistApplicationEntity artistApplicationEntity){
            return GetArtistApplicationResponse.builder()
                    .artistApplicationId(artistApplicationEntity.getApplication_id())
                    .locationType(artistApplicationEntity.getLocationType().getDesc())
                    .sector(artistApplicationEntity.getSector().getDesc())
                    .copyrightName(artistApplicationEntity.getCopyrightName())
                    .albumName(artistApplicationEntity.getAlbumName())
                    .artistName(artistApplicationEntity.getArtistName())
                    .authorName(artistApplicationEntity.getAuthorName())
                    .status(artistApplicationEntity.getStatus().getDesc())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class GetArtistApplicationsResponse {
        private Long artistApplicationId;
        private Date createAt;
        private String status;
    }




}
