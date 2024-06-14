package com.surfer.apiserver.api.song.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongReplyReq {
    private String songReplyContent;
}
