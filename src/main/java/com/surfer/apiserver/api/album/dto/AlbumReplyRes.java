package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.domain.database.entity.AlbumReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlbumReplyRes {
    private Long albumReplySeq;
    private String albumReplyRegdate;
    private String albumReplyContent;
    private int albumReplyLike;
    private String albumReplyCordate;
    private Boolean albumReplyCorrect;

    private Long memberSeq;
    private String nickname;

    public AlbumReplyRes(AlbumReplyEntity albumReplyEntity){
        this.albumReplySeq = albumReplyEntity.getAlbumReplySeq();

        //원하는 날짜 형태 문자열로 변환
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.albumReplyRegdate = formatter.format(albumReplyEntity.getAlbumReplyRegdate());

        this.albumReplyContent = albumReplyEntity.getAlbumReplyContent();
        this.albumReplyLike = albumReplyEntity.getAlbumReplyLike();

        this.albumReplyCordate = formatter.format(albumReplyEntity.getAlbumReplyCordate());

        this.albumReplyCorrect = albumReplyEntity.getAlbumReplyCorrect();

        this.memberSeq = albumReplyEntity.getMemberEntity().getMemberId();
//        this.nickname = albumReplyEntity.getMemberEntity().getNickname();
    }
}
