package com.surfer.apiserver.api.song.dto;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SongReq {
    private Long songSeq;
    private String songTitle;
    private int songNumber;
    private String soundSourceName;
    private Boolean songState;
    private List<SongSingerEntity> singers;

    public SongReq(SongEntity songEntity){
        this.songSeq = songEntity.getSongSeq();
        this.songTitle = songEntity.getSongTitle();
        this.songNumber = songEntity.getSongNumber();
        this.soundSourceName = songEntity.getSoundSourceName();
        this.songState = songEntity.getSongState();
        this.singers = songEntity.getSongSingerEntityList();
    }
}
