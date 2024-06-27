package com.surfer.apiserver.api.song.dto;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Data
public class ResponseSongByGenreDTO {
    List<ImageAndSongDTO> songs;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ImageAndSongDTO{
        SongEntity song;
        String url;
        String singer;
    }
}
