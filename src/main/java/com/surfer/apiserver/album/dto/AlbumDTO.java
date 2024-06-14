package com.surfer.apiserver.album.dto;

import com.surfer.apiserver.domain.database.entity.MemberEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumDTO {

    private Long albumSeq;
    private String albumTitle;
    private Date releaseDate;
    private String agency;
    private String albumContent;
    private String albumImage;
    private int albumState;
    private MemberEntity memberEntity;
    private Long memberId;
    List<String> albumSingerNameList;


     private Date albumRegDate;



}
