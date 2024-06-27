package com.surfer.apiserver.api.album.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.surfer.apiserver.api.album.dto.AlbumReq;
import com.surfer.apiserver.api.album.dto.AlbumSingerDTO;
import com.surfer.apiserver.api.album.dto.SongDTO;
import com.surfer.apiserver.api.album.dto.SongSingerDTO;
import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.*;
import com.surfer.apiserver.domain.database.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumSingerRepository albumSingerRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private SongSingerRepository songSingerRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    public AlbumServiceImpl(SongRepository songRepository, SongSingerRepository songSingerRepository, AlbumRepository albumRepository, AlbumSingerRepository albumSingerRepository) {

        this.songRepository = songRepository;
        this.songSingerRepository = songSingerRepository;
        this.albumRepository = albumRepository;
        this.albumSingerRepository = albumSingerRepository;
    }

    //등록 신청 앨범 리스트 찾기
    @Override
    public List<AlbumEntity> findAllByMemberEntityId(Long memberId) {

        //멤버 id가 있는지 확인
        if (memberId == null) {
            throw new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST);
        }
        List<AlbumEntity> albumEntities = albumRepository.findAllAlbum(memberId);

        return albumEntities;
    }

    //마이페이지 앨범 상세보기
    @Override
    public AlbumEntity findAlbum(Long albumSeq) {

        //앨범 시퀀스가 들어왔는지 확인
        if (albumSeq == null) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST);
        }
        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElseThrow(
                () -> new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST));

        return albumEntity;
    }

    //앨범 가수 리스트
    @Override
    public List<AlbumSingerEntity> findAlbumSingerList(AlbumEntity albumEntity) {

        //앨범 앤터티 존재여부 확인
        if (albumEntity == null) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_ENTITY, HttpStatus.BAD_REQUEST);
        }

        List<AlbumSingerEntity> list = albumSingerRepository.findAllByAlbum(albumEntity);

        return list;
    }

    // 여러개의 파일 업로드
    @Override
    public Map<Integer, String> uploadFile(List<MultipartFile> multipartFile, AlbumReq albumReq) throws BusinessException {
        List<String> fileNameList = new ArrayList<>();
        Map<Integer, String> fileNameMap = new HashMap<>();

        String albumIamgeName = albumImageName(albumReq);
        fileNameMap.put(999, albumIamgeName);
        //가수이름 추출
        List<SongDTO> songDTOList = albumReq.getSongEntities();
        Map<Integer, String> songSinger = new HashMap<>();
        Integer key = 1;
        for (SongDTO songDTO : songDTOList) {
            List<SongSingerDTO> songSingerDTOList = songDTO.getSongSingerEntities();
            StringBuilder result = new StringBuilder();
            for (SongSingerDTO singerDTO : songSingerDTOList) {
                String singerName = singerDTO.getSongSingerName();
                if (!result.isEmpty()) {
                    result.append(",");
                }
                result.append(singerName);

            }

            // 결과 문자열을 Map에 추가
            songSinger.put(key, result.toString());
            key = key + 1;

        }
        // 최종 Map 출력
        System.out.println("map에 저장된 songsinger");
        songSinger.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));

        int no = 1;
        for (MultipartFile file : multipartFile) {

            String idxFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (idxFileName.equals(".mp3")) {

                StringBuilder songName = new StringBuilder();

                songName.append(songSinger.get(no));
                songName.append("-");
                songName.append(file.getOriginalFilename());

                fileNameMap.put(no, songName.toString());
                no = no + 1;

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = file.getInputStream()) {
                    s3Client.putObject(new PutObjectRequest(bucket, songName.toString(), inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
//                throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
                }

            } else {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try (InputStream inputStream = file.getInputStream()) {
                    s3Client.putObject(new PutObjectRequest(bucket, albumIamgeName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
//                throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
                }
            }
            ;
        }
        ;
        return fileNameMap;
    }


    //알맞은 image이름 song이름 저장
    public void albumSave(AlbumReq albumReq, Map<Integer, String> fielNameMap, Long memberId) throws BusinessException {

        //Map에 저장된 albumImageName을 albumReq에 저장
        albumReq.setAlbumImage(fielNameMap.get(999));

        // 예외 처리: Map에 999 키가 존재하지 않는 경우
        if (!fielNameMap.containsKey(999)) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_IMAGE, HttpStatus.BAD_REQUEST);
        }

        //Map에 저장된 file이름을 각각에 해당 곡에 저장
        List<SongDTO> songDTOList = albumReq.getSongEntities();

        if (songDTOList == null || songDTOList.isEmpty()) {
            throw new BusinessException(ApiResponseCode.FAILED_LOAD_SONGLIST, HttpStatus.BAD_REQUEST);
        }

        Integer fileNo = 1;
        for (SongDTO songDTO : songDTOList) {
            songDTO.setSoundSourceName(fielNameMap.get(fileNo));
            System.out.println("singerNameMap.get(singerNo) = " + fielNameMap.get(fileNo));
            fileNo = fileNo + 1;
        }


        System.out.println("albumReq.getAlbumImage() = " + albumReq.getAlbumImage());
        System.out.println("end============================end");
        //albumReq를 각각의 entity에 할당후 저장
        changAlbum(albumReq, memberId);
    }

    //dto를 entity로 변환 후 db 저장
    public void changAlbum(AlbumReq albumReq, Long memberId) {

        //멤버 id가 있는지 확인
        if (memberId == null) {
            throw new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST);
        }

        //앨범저장
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumTitle(albumReq.getAlbumTitle());
        albumEntity.setAgency(albumReq.getAgency());
        albumEntity.setAlbumContent(albumReq.getAlbumContent());
        albumEntity.setAlbumImage(albumReq.getAlbumImage());


        albumEntity.setReleaseDate(albumReq.getReleaseDate());
        System.out.println("----------------------------------");
        System.out.println(albumReq.getReleaseDate());
        System.out.println("----------------------------------");


        albumEntity.setAlbumState(albumReq.getAlbumState());
        albumEntity.setMemberEntity(MemberEntity.builder().memberId(memberId).build());

        AlbumEntity savedAlbumEntity = albumRepository.save(albumEntity);

        //앨범 가수 저장
        List<AlbumSingerDTO> albumSingerList = albumReq.getAlbumSingerEntities();
        for (AlbumSingerDTO albumSingerDTO : albumSingerList) {
            AlbumSingerEntity albumSingerEntity = new AlbumSingerEntity();
            albumSingerEntity.setAlbumSingerName(albumSingerDTO.getAlbumSingerName());
            albumSingerEntity.setAlbumEntity(savedAlbumEntity);
            albumSingerRepository.save(albumSingerEntity);
        }

        //수록곡 저장
        List<SongDTO> songDTOList = albumReq.getSongEntities();
        for (SongDTO songDTO : songDTOList) {
            SongEntity songEntity = new SongEntity();
            songEntity.setSongTitle(songDTO.getSongTitle());
            songEntity.setSongNumber(songDTO.getSongNumber());
            songEntity.setLyrics(songDTO.getLyrics());
            songEntity.setGenre(songDTO.getGenre());
            songEntity.setSongState(songDTO.getSongState());
            songEntity.setSoundSourceName(songDTO.getSoundSourceName());
            songEntity.setProducer(songDTO.getProducer());
            songEntity.setAlbumEntity(savedAlbumEntity);

            songRepository.save(songEntity);

            List<SongSingerDTO> songSingerDTOList = songDTO.getSongSingerEntities();
            for (SongSingerDTO songSingerDTO : songSingerDTOList) {
                SongSingerEntity songSingerEntity = new SongSingerEntity();
                songSingerEntity.setSongSingerName(songSingerDTO.getSongSingerName());
                songSingerEntity.setSongEntity(songEntity);

                songSingerRepository.save(songSingerEntity);
            }
        }
    }

    //앨범 이름 생성하기
    public String albumImageName(AlbumReq albumReq) {
        StringBuilder albumImageName = new StringBuilder();

        Date date = new Date();
        albumImageName.append(date);
        albumImageName.append("-");
        albumImageName.append(albumReq.getAlbumImage());

        return String.valueOf(albumImageName);
    }

    /*
     * 앨범 이미지 url찾기 이거 사용!
     * */
    //앨범 이미지 (albumIamge로) url 찾기
    @Override
    public URL generateAlbumImgFileUrl(String albumImage) {

        //앨범 이미지 존재 확인
        if (albumImage == null) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_IMAGE, HttpStatus.BAD_REQUEST);

        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, albumImage)
                        .withMethod(com.amazonaws.HttpMethod.GET);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);

    }

    //앨범 이미지 (albumSeq로) url 찾기
    public URL findAlbumUrl(Long albumSeq) {

        //앨범정보
        AlbumEntity albumEntity = findAlbum(albumSeq);


        //앨범 시퀀스가 들어왔는지 확인
        if (albumSeq == null) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST);

        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, albumEntity.getAlbumImage())
                        .withMethod(com.amazonaws.HttpMethod.GET);

        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);

    }


    //신청한 앨범 삭제
    @Override
    public void deleteAlbum(Long albumSeq) {
        System.out.println("여기까지3");

        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElse(null);

        //앨범 시퀀스가 있는지 확인
        if (!albumEntity.getAlbumSeq().equals(albumSeq)) {
            throw new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST);
        }
        System.out.println("albumSeq = " + albumEntity.getAlbumSeq());
        albumRepository.deleteById(albumSeq);

    }

    @Override
    public void updateAlbumStatus(Long albumSeq, int albumState) {
        AlbumEntity albumEntity = albumRepository.findById(albumSeq).orElseThrow(
                () -> new BusinessException(ApiResponseCode.INVALID_ALBUM_ID, HttpStatus.BAD_REQUEST));
        albumEntity.setAlbumState(albumState);
        albumRepository.save(albumEntity);
    }

    //유저 권한 확인
    @Override
    public String userAuthorityCheck() {
        System.out.println("3들어와?");
        Long memberId = Long.valueOf(AES256Util.decrypt(SecurityContextHolder.getContext().getAuthentication().getName()));
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_MEMBER_ID, HttpStatus.BAD_REQUEST));

        String userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


//        System.out.println("확인 member = " + member);
//        String userAuthority = AES256Util.decrypt(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        System.out.println("확인 userAuthority = " + userAuthority);
        if (userAuthority.equals("ROLE_SINGER")) {
        } else {
            new BusinessException(ApiResponseCode.FAILED_FIND_SINGER, HttpStatus.BAD_REQUEST);
        }
        return userAuthority;
    }
}
