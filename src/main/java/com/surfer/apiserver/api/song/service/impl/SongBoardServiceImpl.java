package com.surfer.apiserver.api.song.service.impl;

import com.surfer.apiserver.api.song.dto.ProducerDTO;
import com.surfer.apiserver.api.song.dto.SongReplyReq;
import com.surfer.apiserver.api.song.dto.SongReplyRes;
import com.surfer.apiserver.api.song.service.SongBoardService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.*;
import com.surfer.apiserver.domain.database.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SongBoardServiceImpl implements SongBoardService {

    //페이지 처리를 위해 추가
    private final static int PAGE_COUNT=5; //한 페이지 당 댓글 5개

    @Autowired
    private SongRepository songBoardRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongReplyRepository songReplyRepository;

    @Autowired
    private SongSingerRepository songSingerRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SongReplyLikeRepository songReplyLikeRepository;

    @Override
    public SongEntity selectById(Long seq) {
        System.out.println("seq = "+seq);
        //곡 seq에 해당하는 곡이 있는지 조회한다.
        SongEntity songEntity= songBoardRepository.findById(seq).orElseThrow(
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

        //재생 수 증가
        songEntity.setRecentlyPlayedCount(songEntity.getRecentlyPlayedCount()+1);
        songEntity = songBoardRepository.save(songEntity);

        return songEntity;
    }

    @Override
    public void songReplyInsert(SongReplyReq replyEntity, Long memberSeq, Long songSeq) {

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //곡 seq에 해당하는 곡이 있는지 조회한다.
        SongEntity songEntity= songBoardRepository.findById(songSeq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );

        songReplyRepository.save(
                SongReplyEntity.builder()
                        .songReplyContent(replyEntity.getSongReplyContent())
                        .memberEntity(member)
                        .songEntity(songEntity)
                .build());
    }

    @Override
    public void songReplyUpdate(Long songSeq, SongReplyReq songReplyReq, Long memberSeq, Long replySeq) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다
        SongReplyEntity songReplyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        //곡 seq와 댓글에 저장된 곡 seq가 일치하는지 확인
        if(!songSeq.equals(songReplyEntity.getSongEntity().getSongSeq())){
            throw new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST);
        }

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //댓글 작성자와 수정하려는 사람이 일치하는지 확인
        if(!member.getMemberId().equals(songReplyEntity.getMemberEntity().getMemberId())){
            throw new BusinessException(ApiResponseCode.FAILED_UPDATE_REPLY, HttpStatus.BAD_REQUEST);
        }

        //댓글 내용을 새로 입력된 내용으로 수정한다.
        songReplyEntity.setSongReplyContent(songReplyReq.getSongReplyContent());
        //수정여부를 true로 변경한다.
        songReplyEntity.setSongReplyCorrect(true);
    }

    @Override
    public void songReplyDelete(Long songSeq, Long memberSeq, Long replySeq) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다.
        SongReplyEntity replyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        //곡 seq와 댓글에 저장된 곡 seq가 일치하는지 확인
        if(!replyEntity.getSongEntity().getSongSeq().equals(songSeq)){
            throw new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST);
        }

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //댓글 작성자와 삭제하려는 사람이 일치하는지 확인
        if(!member.getMemberId().equals(replyEntity.getMemberEntity().getMemberId())){
            throw new BusinessException(ApiResponseCode.FAILED_UPDATE_REPLY, HttpStatus.BAD_REQUEST);
        }

        //댓글 삭제
        songReplyRepository.deleteById(replyEntity.getSongReplySeq());
    }

    @Override
    public Page<SongReplyRes> getSongReplyRegList(SongEntity songEntity, int nowPage) {
        //댓글 등록일 순으로 정렬(내림차순)
        Pageable pageable =
                PageRequest.of(nowPage-1, PAGE_COUNT, Sort.Direction.DESC, "songReplyRegdate");

        return songReplyRepository.findAllBySong(songEntity, pageable);
    }

    @Override
    public Page<SongReplyRes> getSongReplyLikeList(SongEntity songEntity, int nowPage) {
        Pageable pageable =
                PageRequest.of(nowPage-1, PAGE_COUNT, Sort.Direction.DESC, "songReplyLike");
        return songReplyRepository.findAllBySong(songEntity, pageable);
    }

    @Override
    public List<SongSingerEntity> getSongSingerList(SongEntity songEntity) {
        return songSingerRepository.findAllBySong(songEntity);
    }

    @Override
    public ProducerDTO getProducer(String producer) {
        List<String> composers = new ArrayList<>();
        List<String> lyricists = new ArrayList<>();
        List<String> arrangers = new ArrayList<>();

        if(producer != null && !producer.isEmpty()){
            String[] producerArray = producer.split("/");
            String composerList = producerArray[0];
            String lyricistList = producerArray[1];
            String arrangerList = producerArray[2];

            String[] composerArray = composerList.split(",");
            composers = new ArrayList<>();
            for(int i=0; i<composerArray.length; i++){composers.add(composerArray[i]);}

            String[] lyricistArray = lyricistList.split(",");
            lyricists = new ArrayList<>();
            for(int i=0; i<lyricistArray.length; i++){lyricists.add(lyricistArray[i]);}

            String[] arrangerArray = arrangerList.split(",");
            arrangers = new ArrayList<>();
            for(int i=0; i<arrangerArray.length; i++){arrangers.add(arrangerArray[i]);}
        }

        return ProducerDTO.builder().composerList(composers).lyricistList(lyricists).arrangerList(arrangers).build();
    }

    @Override
    public Boolean songReplyLike(Long songSeq, Long memberSeq, Long replySeq) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다
        SongReplyEntity songReplyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        //곡 seq에 해당하는 곡이 있는지 조회한다.
        songBoardRepository.findById(songSeq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //db에서 댓글 좋아요에 관한 데이터를 찾는다
        SongReplyLikeEntity songReplyLike
                = songReplyLikeRepository.findByMemberAndSongReply(member.getMemberId(), songReplyEntity.getSongReplySeq());

        if(songReplyLike == null){
            //좋아요 누르지 않은 댓글
            return Boolean.FALSE;
        }else{
            //좋아요 누른 댓글
            return Boolean.TRUE;
        }
    }

    @Override
    public void songReplyLikeInsert(Long songSeq, Long memberSeq, Long replySeq) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다
        SongReplyEntity songReplyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        //곡 seq에 해당하는 곡이 있는지 조회한다.
        songBoardRepository.findById(songSeq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //db에서 댓글 좋아요에 관한 데이터를 찾는다
        SongReplyLikeEntity songReplyLike
                = songReplyLikeRepository.findByMemberAndSongReply(member.getMemberId(), songReplyEntity.getSongReplySeq());

        if(songReplyLike == null){
            songReplyLikeRepository.save(
                    SongReplyLikeEntity.builder()
                            .memberEntity(member)
                            .songReplyEntity(songReplyEntity)
                            .build()
            );
        }else{
            //이미 존재함
            throw new BusinessException(ApiResponseCode.FAILED_UPDATE_REPLY, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void songReplyLikeUpdate(Long replySeq, Boolean isLike) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다
        SongReplyEntity replyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        if(isLike){
            //추천수 1만큼 증가시킨다
            replyEntity.setSongReplyLike(replyEntity.getSongReplyLike()+1);
        }else{
            //추천수 1만큼 감소시킨다.
            replyEntity.setSongReplyLike(replyEntity.getSongReplyLike()-1);
        }
    }

    @Override
    public void songReplyLikeDelete(Long songSeq, Long memberSeq, Long replySeq) {
        //db에서 댓글 seq에 해당하는 댓글을 찾는다
        SongReplyEntity songReplyEntity = songReplyRepository.findById(replySeq).orElseThrow(
                ()->new BusinessException(ApiResponseCode.INVALID_REPLY_ID, HttpStatus.BAD_REQUEST)
        );

        //곡 seq에 해당하는 곡이 있는지 조회한다.
        songBoardRepository.findById(songSeq).orElseThrow(
                ()-> new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST)
        );

        //db에 회원이 존재하는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberSeq);
        if(memberEntity.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET, HttpStatus.BAD_REQUEST);
        }
        MemberEntity member = memberEntity.get();

        //db에서 댓글 좋아요에 관한 데이터를 찾는다
        SongReplyLikeEntity songReplyLike
                = songReplyLikeRepository.findByMemberAndSongReply(member.getMemberId(), songReplyEntity.getSongReplySeq());

        if(songReplyLike != null){
            songReplyLikeRepository.delete(songReplyLike);
        }else{
            //데이터 존재 안함
            throw new BusinessException(ApiResponseCode.FAILED_DELETE_LIKE, HttpStatus.BAD_REQUEST);
        }
    }
}
