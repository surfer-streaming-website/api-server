package com.surfer.apiserver.api.album.service.impl;

import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.repository.AlbumRepository;
import com.surfer.apiserver.domain.database.repository.AlbumSingerRepository;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import com.surfer.apiserver.domain.database.repository.SongSingerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    private final SongRepository songRepository;
    private final SongSingerRepository songSingerRepository;
    private final AlbumRepository albumRepository;
    private final AlbumSingerRepository albumSingerRepository;

    @Autowired
    public AlbumServiceImpl(SongRepository songRepository, SongSingerRepository songSingerRepository, AlbumRepository albumRepository, AlbumSingerRepository albumSingerRepository) {

        this.songRepository = songRepository;
        this.songSingerRepository = songSingerRepository;
        this.albumRepository = albumRepository;
        this.albumSingerRepository = albumSingerRepository;
    }

    @Override
    public void saveAlbum(AlbumEntity albumEntity) {
        albumRepository.save(albumEntity);

    }


    //등록 신청 앨범 리스트 찾기
    @Override
    public List<AlbumEntity> findAllByMemberEntityId(Long memberId) {

        List<AlbumEntity> albumEntities= albumRepository.findAllAlbum(memberId);

        return albumEntities;
    }

    //마이페이지 앨범 상세보기
    @Override
    public AlbumEntity findAlbum(Long albumSeq) {

        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElseThrow(
                () -> new BusinessException(ApiResponseCode.INVALID_ALBUM_ID,HttpStatus.BAD_REQUEST));

        return albumEntity;
    }
    
    //앨범 가수 리스트
    @Override
    public List<AlbumSingerEntity> findAlbumSingerList(AlbumEntity albumEntity) {

        List<AlbumSingerEntity> list = albumSingerRepository.findAllByAlbum(albumEntity);

        return list;
    }

    //신청한 앨범 삭제
    @Override
    public void deleteAlbum(Long albumSeq) {

        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElse(null);

        if(!albumEntity.getAlbumSeq().equals(albumSeq)) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST);
        }
        albumRepository.deleteById(albumSeq);

    }



 /*   @Override
    public List<AlbumEntity> findAllByMemberEntityId(Long memberId) {

        List<AlbumEntity> albumEntities= albumRepository.findAllAlbum(memberId);

        return albumEntities;
    }

    //마이페이지 앨범 상세보기
    @Override
    public AlbumEntity findAlbum(Long albumSeq) {

        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElse(null);

        return albumEntity;
    }



*/




/*
    @Override
    public void saveAlbum(AlbumReq albumReq) {
        AlbumDTO albumDTO = albumReq.getAlbumDTO();
        List<String> albumSingerNameList = albumDTO.getAlbumSingerNameList();
        List<SongDTO> songDTOList = albumReq.getSongDTOList();


        // DTO를 엔터티로 변환
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumTitle(albumDTO.getAlbumTitle());
        albumEntity.setAgency(albumDTO.getAgency());
        albumEntity.setAlbumImage(albumDTO.getAlbumImage());
        albumEntity.setAlbumContent(albumDTO.getAlbumContent());
        albumEntity.setReleaseDate(albumDTO.getReleaseDate());

        albumEntity.setMemberEntity(albumDTO.getMemberEntity());

        albumRepository.save(albumEntity);

        for (String singer : albumSingerNameList){
            AlbumSingerEntity albumSingerEntity = new AlbumSingerEntity();
            albumSingerEntity.setAlbumSingerName(singer);
            albumSingerEntity.setAlbumEntity(albumEntity);

            albumSingerRepository.save(albumSingerEntity);
        }

        for(SongDTO songDTO : songDTOList){

            SongEntity songEntity = new SongEntity();
            songEntity.setAlbumEntity(albumEntity);
            songEntity.setSongTitle(songDTO.getSongTitle());
            songEntity.setSongNumber(songDTO.getSongNumber());
            songEntity.setLyrics(songDTO.getLyrics());
            songEntity.setGenre(songDTO.getGenre());
            songEntity.setSongState(songDTO.getSongState());
            songEntity.setSoundSourceName(songDTO.getSoundSourceName());
            songEntity.setProducer(songDTO.getProducer());

            songRepository.save(songEntity);

            for (String songSingerName : songDTO.getSongSingerNameList()) {
                SongSingerEntity songSingerEntity = new SongSingerEntity();
                songSingerEntity.setSongSingerName(songSingerName);
                songSingerEntity.setSongEntity(songEntity);

                songSingerRepository.save(songSingerEntity);
            }

        }
    }*/

}
