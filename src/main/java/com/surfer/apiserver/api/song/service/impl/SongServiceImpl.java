package com.surfer.apiserver.api.song.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.repository.AlbumRepository;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class SongServiceImpl implements SongService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public SongEntity selectById(Long seq) {

        System.out.println("seq = "+seq);
        //곡 seq에 해당하는 곡이 있는지 조회한다.
        SongEntity songEntity= songRepository.findById(seq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );
        //해당 곡이 포함된 앨범이 존재하는지 조회하고,
        //해당 곡이 포함된 앨범이 등록완료 상태인지 조회한다.
        Long albumSeq = songEntity.getAlbumEntity().getAlbumSeq();
        albumRepository.findById(albumSeq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST)
        );
        int albumState = songEntity.getAlbumEntity().getAlbumState();
        //0: 심사중, 1: 등록완료, 2: 반려됨 -> 맞는지 다시 한번 확인
        if(albumState != 1){
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_STATE, HttpStatus.BAD_REQUEST);
        }

        return songEntity;
    }

    public URL generateSongFileUrl(String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(com.amazonaws.HttpMethod.GET);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }




}
