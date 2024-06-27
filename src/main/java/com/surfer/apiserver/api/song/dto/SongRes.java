package com.surfer.apiserver.api.song.dto;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SongRes {
    private Long songSeq;
    private String songTitle;
    private int songNumber;
    private Boolean songState;

    private String lyrics;
    private int totalPlayedCount;
    private int recentlyPlayedCount;
    private String genre;
    private String soundSourceName;
    private String soundSourceUrl;

    private Page<SongReplyRes> replies;

    private List<SongSingerEntity> singers;
    private List<String> singerList;

    private Long albumSeq;
    private String albumTitle;
    private String albumImage;

    private ProducerDTO producerDTO;

    public SongRes(SongEntity songEntity, Page<SongReplyRes> replies, List<SongSingerEntity> singers, ProducerDTO producerDTO){
        this.songSeq = songEntity.getSongSeq();
        this.songTitle = songEntity.getSongTitle();
        this.songNumber = songEntity.getSongNumber();
        this.songState = songEntity.getSongState();
        this.lyrics = songEntity.getLyrics();
        this.totalPlayedCount = songEntity.getTotalPlayedCount();
        this.recentlyPlayedCount = songEntity.getRecentlyPlayedCount();
        this.soundSourceName = songEntity.getSoundSourceName();
        this.genre = songEntity.getGenre();
        this.replies = replies;
//        this.singers = singers;

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

        this.producerDTO = producerDTO;

        this.albumSeq = songEntity.getAlbumEntity().getAlbumSeq();
        this.albumTitle = songEntity.getAlbumEntity().getAlbumTitle();
        this.albumImage = songEntity.getAlbumEntity().getAlbumImage();
    }


    public SongRes(SongEntity songEntity,List<SongSingerEntity> singers, ProducerDTO producerDTO){
        this.songSeq = songEntity.getSongSeq();
        this.songTitle = songEntity.getSongTitle();
        this.songNumber = songEntity.getSongNumber();
        this.lyrics = songEntity.getLyrics();
        this.genre = songEntity.getGenre();
        this.songState = songEntity.getSongState();
        this.soundSourceName = songEntity.getSoundSourceName();
        this.producerDTO = producerDTO;
        this.singers = singers;


//        this.albumSeq = songEntity.getAlbumEntity().getAlbumSeq();
//        this.albumTitle = songEntity.getAlbumEntity().getAlbumTitle();
    }

    public SongRes(SongEntity songEntity){
        this.songSeq = songEntity.getSongSeq();
        this.songTitle = songEntity.getSongTitle();
        this.songNumber = songEntity.getSongNumber();
        this.lyrics = songEntity.getLyrics();
        this.genre = songEntity.getGenre();
        this.soundSourceName = songEntity.getSoundSourceName();
        this.songState = songEntity.getSongState();
        this.singers = songEntity.getSongSingerEntities();
    }

}
