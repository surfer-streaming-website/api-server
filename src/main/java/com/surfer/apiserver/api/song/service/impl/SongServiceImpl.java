package com.surfer.apiserver.api.song.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.SongLikeEntity;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.SongLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SongLikeRepository songLikeRepository;

    @Override
    public SongEntity selectById(Long seq) {
        // 곡 seq에 해당하는 곡이 있는지 조회한다.
        SongEntity songEntity = songRepository.findById(seq).orElseThrow(
                () -> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );
        int albumState = songEntity.getAlbumEntity().getAlbumState();
        if (albumState != 1) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_STATE, HttpStatus.BAD_REQUEST);
        }
        return songEntity;
    }

    @Override
    public URL generateSongFileUrl(String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(com.amazonaws.HttpMethod.GET);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    @Override
    public boolean isSongLikedByUser(Long songId, Long memberId) {
        SongEntity song = selectById(songId);
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));
        return songLikeRepository.findBySongAndMember(song, member).isPresent();
    }

    @Override
    public void likeSong(Long songId, Long memberId) {
        SongEntity song = selectById(songId);
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));
        if (songLikeRepository.findBySongAndMember(song, member).isPresent()) {
            throw new BusinessException(ApiResponseCode.ALREADY_LIKED, HttpStatus.BAD_REQUEST);
        }
        SongLikeEntity like = SongLikeEntity.builder().song(song).member(member).build();
        songLikeRepository.save(like);
    }

    @Override
    public void unlikeSong(Long songId, Long memberId) {
        SongEntity song = selectById(songId);
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));
        SongLikeEntity like = songLikeRepository.findBySongAndMember(song, member)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_LIKED, HttpStatus.BAD_REQUEST));
        songLikeRepository.delete(like);
    }

    @Override
    public long countSongLikes(Long songId) {
        SongEntity song = selectById(songId);
        return songLikeRepository.countBySong(song);
    }
}
