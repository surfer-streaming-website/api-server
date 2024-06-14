package com.surfer.apiserver.api.playlist.service.impl;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.*;
import com.surfer.apiserver.domain.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final MemberRepository memberRepository;
    private final PlaylistGroupRepository playlistGroupRepository;
    private final PlaylistTagRepository playlistTagRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final PlaylistLikeRepository playlistLikeRepository;
    private final SongTestRepository songTestRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public int createNewPlaylist(PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO, Long songSeq) {
//        Authentication authentication = playlistGroupRequestDTO.getAuthentication();
        PlaylistGroupEntity playlistGroupEntity =
                playlistGroupRequestDTO.toPlaylistGroupEntityWithOutTag(playlistGroupRequestDTO);
        playlistGroupEntity.setMemberEntity(getCurrentMember());

        PlaylistGroupEntity playlistGroup = playlistGroupRepository.save(playlistGroupEntity);

        for (String tag : playlistGroupRequestDTO.getTagList()) {
            playlistTagRepository.save(
                    PlaylistTagEntity.builder()
                            .tagEntity(tagRepository.findByTagName(tag)
                                    .orElseThrow(() -> new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST_LIKE, HttpStatus.BAD_REQUEST)))
                            .playlistGroupEntity(playlistGroup)
                            .build()
            );
        }

        playlistTrackRepository.save(PlaylistTrackEntity.builder()
                .playlistGroupEntity(playlistGroup)
                .songTestEntity(findSongTest(songSeq))
                .build());

        return 1;
    }

    @Override
    public int insertSongIntoPlaylist(Long songSeq, Long playlistGroupSeq) {
        playlistTrackRepository.save(PlaylistTrackEntity.builder()
                .songTestEntity(findSongTest(songSeq))
                .playlistGroupEntity(findPlaylistGroup(playlistGroupSeq))
                .build());

        return 1;
    }

    @Override
    public List<PlaylistDTO.PlaylistGroupResponseDTO> getAllPlaylists() {
        List<PlaylistGroupEntity> playlists = playlistGroupRepository.findByMember(getCurrentMember());
        if (playlists.isEmpty()) {
            throw new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST, HttpStatus.BAD_REQUEST);
        }

        return playlists.stream().map(PlaylistDTO.PlaylistGroupResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PlaylistDTO.PlaylistGroupResponseDTO getPlaylistById(Long playlistGroupSeq) {
        return new PlaylistDTO.PlaylistGroupResponseDTO(findPlaylistGroup(playlistGroupSeq));
    }

    @Override
    public int changePlaylistNameAndIsOpen(Long playlistGroupSeq, PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO) {
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);

        playlistGroupEntity.setPlaylistName(playlistGroupRequestDTO.getPlaylistName());
        playlistGroupEntity.setIsOpen(playlistGroupRequestDTO.getIsOpen());

        playlistGroupRepository.save(playlistGroupEntity);

        return 1;
    }

    @Override
    public int deletePlaylistById(Long playlistGroupSeq) {
        playlistGroupRepository.deleteById(playlistGroupSeq);

        return 1;
    }

    @Override
    public int deleteSongFromPlaylistById(Long playlistGroupSeq, Long songSeq) {
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);
        SongTestEntity songTestEntity = findSongTest(songSeq);

        PlaylistTrackEntity playlistTrackEntity = playlistTrackRepository
                .findByPlaylistGroupSeqAndSongSeq(playlistGroupEntity, songTestEntity)
                        .orElseThrow(()->new BusinessException(ApiResponseCode.FAILED_ACCESS_PLAYLIST, HttpStatus.BAD_REQUEST));

        playlistTrackRepository.delete(playlistTrackEntity);

        //해당 플레이리스트에 더이상 남아있는 곡이 없다면 플레이리스트 삭제
        if(playlistTrackRepository.findByPlaylistGroupSeq(playlistGroupEntity).isEmpty()) {
            deletePlaylistById(playlistGroupSeq);
        }

        return 1;
    }

    @Override
    public List<PlaylistDTO.PlaylistLikeResponseDTO> likedPlaylists() {
        List<PlaylistLikeEntity> playlistLikeEntityList = playlistLikeRepository.findByMember(getCurrentMember());
        if(playlistLikeEntityList.isEmpty()) {
            throw new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST_LIKE, HttpStatus.BAD_REQUEST);
        }
        return playlistLikeEntityList.stream().map(PlaylistDTO.PlaylistLikeResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public int insertPlaylistLikeById(Long playlistGroupSeq) {
        playlistLikeRepository.save(PlaylistLikeEntity.builder()
                .playlistGroupEntity(findPlaylistGroup(playlistGroupSeq))
                .memberEntity(getCurrentMember())
                .build());

        return 1;
    }

    @Override
    public int deletePlaylistLikeById(Long playlistGroupSeq) {
        MemberEntity memberEntity = getCurrentMember();
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);

        PlaylistLikeEntity playlistLikeEntity = playlistLikeRepository.liked(memberEntity, playlistGroupEntity)
                .orElseThrow(()->new BusinessException(ApiResponseCode.FAILED_ACCESS_PLAYLIST_LIKE, HttpStatus.BAD_REQUEST));

        playlistLikeRepository.delete(playlistLikeEntity);

        return 1;
    }

    /**
     * JWT 토큰을 사용하여 현재 로그인 중인 사용자 정보 가져오기
     */
    private MemberEntity getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return memberRepository
                .findById(Long.parseLong(AES256Util.decrypt(authentication.getName())))
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_API_ACCESS_TOKEN, HttpStatus.BAD_REQUEST));
    }

    /**
     * playlistSeq 를 기준으로 playlistGroupEntity 를 찾아주는 메소드
     */
    private PlaylistGroupEntity findPlaylistGroup(Long playlistGroupSeq) {
        return playlistGroupRepository.findById(playlistGroupSeq)
                .orElseThrow(()->new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST, HttpStatus.BAD_REQUEST));
    }

    /**
     * songSeq 를 기준으로 SongTestEntity 를 찾아주는 메소드
     */
    private SongTestEntity findSongTest(Long songTestSeq) {
        return songTestRepository.findById(songTestSeq)
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_PARAMETER_ERR, HttpStatus.BAD_REQUEST));
    }
}
