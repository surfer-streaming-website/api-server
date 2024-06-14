package com.surfer.apiserver.api.album.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumReplyReq {
    private String albumReplyContent;
}
