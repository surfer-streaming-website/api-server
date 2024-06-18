package com.surfer.apiserver.api.song.dto;

import com.surfer.apiserver.domain.database.entity.SongReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SongReplyRes {
    private Long songReplySeq;
    private String songReplyRegDate;
    private String songReplyContent;
    private int songReplyLike;
    private String songReplyCordate;
    private Boolean songReplyCorrect;

    private Long memberSeq;
    private String nickname;

    public SongReplyRes(SongReplyEntity songReplyEntity){
        this.songReplySeq = songReplyEntity.getSongReplySeq();
        //원하는 날짜 형태 문자열로 변환
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.songReplyRegDate = formatter.format(songReplyEntity.getSongReplyRegdate());
        this.songReplyContent = songReplyEntity.getSongReplyContent();
        this.songReplyLike = songReplyEntity.getSongReplyLike();
        this.songReplyCordate = formatter.format(songReplyEntity.getSongReplyCordate());
        this.songReplyCorrect = songReplyEntity.getSongReplyCorrect();

        this.memberSeq = songReplyEntity.getMemberEntity().getMemberId();
        this.nickname = songReplyEntity.getMemberEntity().getNickname();
    }
}
