package com.surfer.apiserver.api.admin.dto;

import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class AdminDTO {
    @Data
    @AllArgsConstructor
    public static class GetArtistApplicationsResponse {
        private Long artistApplicationId;
        private Date createAt;
        private String status;
    }

    @Data
    public static class ManageArtistApplicationRequest {
        @NotBlank(message = "상태 필수값입니다.")
        private String status;
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

}
