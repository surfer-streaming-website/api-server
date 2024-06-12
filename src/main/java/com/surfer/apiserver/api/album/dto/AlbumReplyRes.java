package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlbumReplyRes {
    private Long albumReplySeq;
    private Date albumReplyRegdate;
    private String albumReplyContent;
    private int albumReplyLike;
    private Date albumReplyCordate;
    private Boolean albumReplyCorrect;

    private Long memberSeq;
    private String nickname;

    public AlbumReplyRes(AlbumReplyEntity albumReplyEntity){
        this.albumReplySeq = albumReplyEntity.getAlbumReplySeq();
        this.albumReplyRegdate = albumReplyEntity.getAlbumReplyRegdate();
        this.albumReplyContent = albumReplyEntity.getAlbumReplyContent();
        this.albumReplyLike = albumReplyEntity.getAlbumReplyLike();
        this.albumReplyCordate = albumReplyEntity.getAlbumReplyCordate();
        this.albumReplyCorrect = albumReplyEntity.getAlbumReplyCorrect();

        this.memberSeq = albumReplyEntity.getMemberEntity().getMemberSeq();
        this.nickname = albumReplyEntity.getMemberEntity().getNickname();
    }
}
