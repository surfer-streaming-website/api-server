package com.surfer.apiserver.api.album.dto;

import com.surfer.apiserver.api.song.dto.SongReq;
import com.surfer.apiserver.api.song.dto.SongRes;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AlbumRes {
    private Long albumSeq;
    private String albumTitle;
    private String releaseDate;
    private String agency;
    private String albumContent;
    private String albumImage;
    private int albumState;
    private String albumRegDate;

    private List<SongReq> songDtoList;

    private Page<AlbumReplyRes> replies;

    private List<AlbumSingerEntity> singers;
    private List<String> singerList;

    private List<SongRes> songList;

    public AlbumRes(AlbumEntity albumEntity, Page<AlbumReplyRes> replies, List<AlbumSingerEntity> singers){
        this.albumSeq = albumEntity.getAlbumSeq();
        this.albumTitle = albumEntity.getAlbumTitle();
        
        //원하는 날짜 형태 문자열로 변환
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.releaseDate = formatter.format(albumEntity.getReleaseDate());

        this.agency = albumEntity.getAgency();
        this.albumContent = albumEntity.getAlbumContent();
        this.albumImage = albumEntity.getAlbumImage();
        this.albumState = albumEntity.getAlbumState();

        //song에 관련해서 필요한 정보만 담았다.
        List<SongReq> songDTOList = new ArrayList<>();
        albumEntity.getSongEntities().forEach(
                song -> {
                    SongReq songDTO = new SongReq(song);
                    songDTOList.add(songDTO);
                }
        );
        this.songDtoList = songDTOList;

        this.replies = replies;

        List<String> singerList = new ArrayList<>();
        List<AlbumSingerEntity> albumSingerEntities = albumEntity.getAlbumSingerEntities();
        int size = albumSingerEntities.size();

        for (int i = 0; i < size; i++) {
            AlbumSingerEntity albumSingerEntity = albumSingerEntities.get(i);
            String singer = albumSingerEntity.getAlbumSingerName();

            singerList.add(singer);

            // 마지막 가수가 아닌 경우에만 쉼표를 추가
            if (i < size - 1) {
                singerList.add(", ");
            }
        }
        this.singerList = singerList;
    }




    //마이페이지 앨범 상세보기
    public AlbumRes(AlbumEntity albumEntity, List<AlbumSingerEntity> singers){
        this.albumSeq = albumEntity.getAlbumSeq();
        this.albumTitle = albumEntity.getAlbumTitle();

        //원하는 날짜 형태 문자열로 변환
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.albumRegDate = formatter.format(albumEntity.getAlbumRegDate());
        this.agency = albumEntity.getAgency();
        this.albumContent = albumEntity.getAlbumContent();
        this.albumImage = albumEntity.getAlbumImage();
        this.albumState = albumEntity.getAlbumState();

        //song에 관련해서 필요한 정보만 담았다.
        List<SongRes> songList = new ArrayList<>();
        albumEntity.getSongEntities().forEach(
                song -> {
                    SongRes songRes = new SongRes(song);
                    songList.add(songRes);
                }
        );
        this.songList = songList;
        this.singers = singers;
    }





}
