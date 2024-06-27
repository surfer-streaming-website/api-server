package com.surfer.apiserver.api.album.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAlbumListAllAdminResponse {
    private Long albumSeq;
    private String albumTitle;
    private String albumSinger;
    private Date albumRegDate;
    private Integer albumState;
}
