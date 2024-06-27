package com.surfer.apiserver.api.song.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSongRankResponse {
    private String songTile;
    private String songAuthor;
    private long songSeq;
    private int rank;
    private String url;
    private Long albumSeq;
}
//14 4 2 2 0