package com.surfer.apiserver.api.song.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSongsResponse {
    private Long songSeq;
    private String songTitle;
    private String singer;
    private String url;
}
