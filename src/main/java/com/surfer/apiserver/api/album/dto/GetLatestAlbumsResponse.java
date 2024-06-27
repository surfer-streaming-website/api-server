package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
public class GetLatestAlbumsResponse {
    private List<AlbumAndSingerAndUrlDTO> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class AlbumAndSingerAndUrlDTO{
        private AlbumEntity album;
        private String singer;
        private String url;
    }
}
