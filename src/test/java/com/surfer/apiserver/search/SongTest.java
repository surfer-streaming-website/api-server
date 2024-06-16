/*
package com.surfer.apiserver.search;

import com.surfer.apiserver.album.controller.AlbumController;
import com.surfer.apiserver.album.dto.AlbumDTO;
import com.surfer.apiserver.album.dto.AlbumReq;
import com.surfer.apiserver.album.dto.SongDTO;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.repository.*;
import com.surfer.apiserver.search.controller.SearchController;
import com.surfer.apiserver.search.dto.*;
import com.surfer.apiserver.search.service.SearchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Commit
@Slf4j
@Transactional
@SpringBootTest

public class SongTest {


    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AlbumController albumController;

    @Autowired
    private SearchController searchController;

    @Autowired
    private SearchServiceImpl searchService;

    @Test
    public void keyword(){
        //ResponseEntity<?> responseEntity = searchController.keywordSearch("마이클3");
        //SearchRes searchRes = searchService.findKeyword("둘리1");
//        SearchRes searchRes = searchService.findKeyword("마이클1");
        SearchRes searchRes = searchService.findKeyword("반가워");

        List<SongSearchDTO> song = searchRes.getSong();
        List<AlbumSearchDTO> album = searchRes.getAlbum();
        List<LyricsSearchDTO> lyrics = searchRes.getLyrics();
        List<PlayListSearchDTO> playlist = searchRes.getPlayList();

        System.out.println("1------------------------------------");
        for(SongSearchDTO songSearchDTO : song){
            System.out.println(songSearchDTO.getAlbumImage() +" , "+songSearchDTO.getSongTitle() +" , "+songSearchDTO.getSongSingerName());

       }
        System.out.println("1------------------------------------");

        System.out.println("2------------------------------------");
        for(AlbumSearchDTO searchDTO : album){
            System.out.println(searchDTO.getAlbumTitle() +" , "+searchDTO.getAlbumSingerName());
        }
        System.out.println("2------------------------------------");

        for(PlayListSearchDTO playListSearchDTO : playlist){
            System.out.println(playListSearchDTO.getPlayListName() +" , "+playListSearchDTO.getAlbumImage() +" , "+ playListSearchDTO.getMemberName());
        }
        System.out.println("3------------------------------------");

        System.out.println("4------------------------------------");
        for(LyricsSearchDTO lyricsSearchDTO : lyrics){
            System.out.println(lyricsSearchDTO.getAlbumImage() +" , "+lyricsSearchDTO.getSongTitle()+" , "+lyricsSearchDTO.getSongSingerName()+" , "+lyricsSearchDTO.getLyrics());

        }
        System.out.println("4-----------------------------------");
    }






    @Commit
    @Test
    public void songSave() {

        MemberEntity member = MemberEntity.builder()
                .memberId(2L)
                .build();

        List<String> albumSingerNames = new ArrayList<>();
            albumSingerNames.add("노트1");
            albumSingerNames.add("노트2");
            albumSingerNames.add("노트3");

        AlbumDTO album = AlbumDTO.builder()
                .albumTitle("앨범 재목12345")
                .releaseDate(new Date())
                .agency("agency")
                .albumContent("앨범소개")
                .albumImage("앨범.jpg")
                .albumState(1)
                .albumRegDate(new Date())
                .memberEntity(member)
                .albumSingerNameList(albumSingerNames)
                .build();

        List<String> songSingerNames = new ArrayList<>();
        songSingerNames.add("마우스1");
        songSingerNames.add("마우스2");
        songSingerNames.add("마우스3");
        List<String> songSingerNames1 = new ArrayList<>();
        songSingerNames1.add("마우스2");
        songSingerNames1.add("키보드1");
        songSingerNames1.add("키보드2");



        SongDTO songDTO = SongDTO.builder()
                .songTitle("음악제목111")
                .songNumber(1)
                .lyrics("반가워111")
                .totalPlayedCount(0)
                .recentlyPlayedCount(0)
                .genre("댄스")
                .producer("홍순호/둘리/마이콜,또치")
                .songState(true)
                .soundSourceName("음원파일명")
                .songSingerNameList(songSingerNames)
                .build();

        SongDTO songDTO1 = SongDTO.builder()
                .songTitle("음악제목222")
                .songNumber(2)
                .lyrics("반가워222")
                .totalPlayedCount(0)
                .recentlyPlayedCount(0)
                .genre("락")
                .producer("김탁구/김치/깍두기,또치")
                .songState(true)
                .soundSourceName("음원파일명22")
                .songSingerNameList(songSingerNames1)
                .build();

        List<SongDTO> songDTOList = new ArrayList<>();
        songDTOList.add(songDTO);
        songDTOList.add(songDTO1);


        AlbumReq albumReq = new AlbumReq();

        albumReq.setAlbumDTO(album);
        albumReq.setSongDTOList(songDTOList);

        albumController.albumSave(albumReq);
    }

*/
/*
    @Test
    void findKeyword() {
        List<SongEntity> list = songRepository.findKeyword("1");
        List<SongEntity> list3 = songRepository.findBySongTitleContaining("title");

        System.out.println("3----------------====================---------------");
        for (SongEntity song : list) {
            System.out.println(song);
        }

        System.out.println("---------------------------------------------------");
        for (SongEntity song : list3) {
            System.out.println(song);
        }
        System.out.println("list = " + list);
        System.out.println("list3 = " + list3);
        System.out.println("3---------------====================----------------");


    }*//*



}
*/
