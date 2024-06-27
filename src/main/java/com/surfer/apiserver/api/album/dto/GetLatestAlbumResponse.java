package com.surfer.apiserver.api.album.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLatestAlbumResponse {
    private String url;
    private String albumTitle;
    private Long albumSeq;
    private String Singer;
}
