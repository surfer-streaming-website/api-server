package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.api.song.dto.SongReq;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlbumRes {
    private Long albumSeq;
    private String albumTitle;
    private Date releaseDate;
    private String agency;
    private String albumContent;
    private String albumImage;
    private int albumState;

    private List<SongReq> songDtoList;

    private Page<AlbumReplyRes> replies;

    private List<AlbumSingerEntity> singers;

    public AlbumRes(AlbumEntity albumEntity, Page<AlbumReplyRes> replies, List<AlbumSingerEntity> singers){
        this.albumSeq = albumEntity.getAlbumSeq();
        this.albumTitle = albumEntity.getAlbumTitle();
        this.releaseDate = albumEntity.getReleaseDate();
        this.agency = albumEntity.getAgency();
        this.albumContent = albumEntity.getAlbumContent();
        this.albumImage = albumEntity.getAlbumImage();
        this.albumState = albumEntity.getAlbumState();

        //song에 관련해서 필요한 정보만 담았다.
        List<SongReq> songDTOList = new ArrayList<>();
        albumEntity.getSongEntityList().forEach(
                song -> {
                    SongReq songDTO = new SongReq(song);
                    songDTOList.add(songDTO);
                }
        );
        this.songDtoList = songDTOList;

        this.replies = replies;
        this.singers = singers;
    }
}