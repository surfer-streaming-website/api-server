package com.surfer.apiserver.api.album.service;

import com.surfer.apiserver.api.album.dto.AlbumReq;
import com.surfer.apiserver.api.album.dto.GetLatestAlbumsResponse;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Map;


public interface AlbumService {

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

    /*
    *   앨범 db저장
    * */
    void albumSave(AlbumReq albumReq, Map<Integer, String> singerNameMap, Long memberId);

    /*
    * 파일 s3업로드
    * */
    Map<Integer,String> uploadFile(List<MultipartFile> multipartFile, AlbumReq albumReq);

//    void deleteFile(String fileName);

    /*
    * 앨범 이미지 url 찾기
    * */
    URL generateAlbumImgFileUrl(String albumImage);

    /*
     * 앨범 상태 변경
     * */
    void updateAlbumStatus(Long albumSeq, int albumState);

    /*
     * 최신 앨범 조회
     * */
    GetLatestAlbumsResponse getLatestAlbums();

    Long getAlbumLikeCountResponse(Long albumSeq);
}
