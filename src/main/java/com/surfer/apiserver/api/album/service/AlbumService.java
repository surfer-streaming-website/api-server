package com.surfer.apiserver.api.album.service;

import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;

import java.util.List;


public interface AlbumService {

    void saveAlbum(AlbumEntity albumEntity);


    /*
     * 신청한 앨범 리스트 찾기
     * */
    List<AlbumEntity> findAllByMemberEntityId(Long memberId);

    /*
     * 앨범 상세보기
     * */
    AlbumEntity findAlbum(Long albumSeq);

    /**
     * 앨범 가수 리스트 찾기
     */
    public List<AlbumSingerEntity> findAlbumSingerList(AlbumEntity albumEntity);

    /*
    * 신청한 앨범 삭제
    * */
    void deleteAlbum(Long albumSeq);

}
