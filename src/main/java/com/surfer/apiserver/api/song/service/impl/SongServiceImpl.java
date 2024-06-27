package com.surfer.apiserver.api.song.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.surfer.apiserver.api.album.service.impl.AlbumServiceImpl;
import com.surfer.apiserver.api.song.dto.GetSongRankResponse;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.surfer.apiserver.api.song.service.SongService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.SongLikeEntity;
import com.surfer.apiserver.domain.database.repository.AlbumRepository;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.SongLikeRepository;
import com.surfer.apiserver.domain.database.repository.custom.CustomSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private CustomSongRepository customSongRepository;
    @Autowired
    private AlbumServiceImpl albumServiceImpl;
    @Autowired
    private AlbumRepository albumRepository;

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


    // 음악 파일 다운로드
    @Override
    public Map<Integer,Object> songFileDownload(Long id, HttpServletRequest request) {

        SongEntity songEntity = this.selectById(id);
        if(songEntity ==null){
            throw new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST);
        }

        String downloadFileName = songEntity.getSoundSourceName();
        String encodedFileName = URLEncoder.encode(downloadFileName, StandardCharsets.UTF_8);
        String fileName = null;
        try {
            fileName = makeFileName(request, Objects.requireNonNullElse(downloadFileName, songEntity.getSongSingerEntities()).toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        S3Object s3Object = s3Client.getObject(bucket, songEntity.getSoundSourceName());
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
        headers.setContentDispositionFormData("attachment", encodedFileName);

        Map<Integer,Object> map = new HashMap<>();

        map.put(1,objectInputStream);
        map.put(2,headers);

        return map;
    }

    //음악 파일 이름 UTF-8로 변환
    private String makeFileName(HttpServletRequest request, String displayFileName) throws UnsupportedEncodingException {
        String header = request.getHeader("User-Agent");

        String encodedFilename = null;
        if (header.contains("MSIE")) {
            encodedFilename = URLEncoder.encode(displayFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        } else if (header.contains("Trident")) {
            encodedFilename = URLEncoder.encode(displayFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        } else if (header.contains("Chrome")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < displayFileName.length(); i++) {
                char c = displayFileName.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, StandardCharsets.UTF_8));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else if (header.contains("Opera")) {
            encodedFilename = "\"" + new String(displayFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
        } else if (header.contains("Safari")) {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"", StandardCharsets.UTF_8);
        } else {
            encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"", StandardCharsets.UTF_8);
        }

        return encodedFilename;

    }

    @Override
    public boolean isSongLikedByUser(Long songId) {
        SongEntity song = selectById(songId);
        Long memberId = Long.valueOf(AES256Util.decrypt(SecurityContextHolder.getContext().getAuthentication().getName()));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));
        return songLikeRepository.findBySongAndMember(song, member).isPresent();
    }

    @Override
    public void likeSong(Long songId) {
        SongEntity song = selectById(songId);
        Long memberId = Long.valueOf(AES256Util.decrypt(SecurityContextHolder.getContext().getAuthentication().getName()));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));
        if (songLikeRepository.findBySongAndMember(song, member).isPresent()) {
            throw new BusinessException(ApiResponseCode.ALREADY_LIKED, HttpStatus.BAD_REQUEST);
        }
        SongLikeEntity like = SongLikeEntity.builder().song(song).member(member).build();
        songLikeRepository.save(like);
    }

    @Override
    public void unlikeSong(Long songId) {
        SongEntity song = selectById(songId);
        Long memberId = Long.valueOf(AES256Util.decrypt(SecurityContextHolder.getContext().getAuthentication().getName()));
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

    @Override
    public List<GetSongRankResponse> getSongRank() {
        List<GetSongRankResponse> songRank = customSongRepository.getSongRank();
        songRank.forEach(response ->{
            response.setUrl(albumServiceImpl.findAlbumUrl(response.getAlbumSeq()).toString());
        });
        return songRank;
    }
}
