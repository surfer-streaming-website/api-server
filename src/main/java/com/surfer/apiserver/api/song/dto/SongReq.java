package com.surfer.apiserver.api.song.dto;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private String soundSourceUrl;
    private Boolean songState;
    private List<SongSingerEntity> singers;
    private List<String> singerList;
    private int totalPlayedCount;

    public SongReq(SongEntity songEntity){
        this.songSeq = songEntity.getSongSeq();
        this.songTitle = songEntity.getSongTitle();
        this.songNumber = songEntity.getSongNumber();
        this.soundSourceName = songEntity.getSoundSourceName();
        this.songState = songEntity.getSongState();
        //this.singers = songEntity.getSongSingerEntities();
        this.totalPlayedCount = songEntity.getTotalPlayedCount();
        List<String> singerList = new ArrayList<>();
        List<SongSingerEntity> songSingerEntities = songEntity.getSongSingerEntities();
        int size = songSingerEntities.size();

        for (int i = 0; i < size; i++) {
            SongSingerEntity songSingerEntity = songSingerEntities.get(i);
            String singer = songSingerEntity.getSongSingerName();

            singerList.add(singer);

            // 마지막 가수가 아닌 경우에만 쉼표를 추가
            if (i < size - 1) {
                singerList.add(", ");
            }
        }
        this.singerList = singerList;
    }
}
