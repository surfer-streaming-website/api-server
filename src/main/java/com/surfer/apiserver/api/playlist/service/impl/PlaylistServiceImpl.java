package com.surfer.apiserver.api.playlist.service.impl;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.constant.CommonCode;
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
    private final TagRepository tagRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional
    public int createNewPlaylist(PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO, Long songSeq) {
        PlaylistGroupEntity playlistGroupEntity =
                playlistGroupRequestDTO.toPlaylistGroupEntityWithOutTag(playlistGroupRequestDTO);
        playlistGroupEntity.setMemberEntity(getCurrentMember());
        playlistGroupEntity = playlistGroupRepository.save(playlistGroupEntity);

        if(playlistGroupRequestDTO.getTagList() != null && !playlistGroupRequestDTO.getTagList().isEmpty()) {
            for (String tag : playlistGroupRequestDTO.getTagList()) {
                playlistTagRepository.save(
                        PlaylistTagEntity.builder()
                                .tagEntity(tagRepository.findByTagName(tag)
                                        .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_TAG_ID, HttpStatus.BAD_REQUEST)))
                                .playlistGroupEntity(playlistGroupEntity)
                                .build()
                );
            }
        }

        playlistTrackRepository.save(PlaylistTrackEntity.builder()
                .playlistGroupEntity(playlistGroupEntity)
                .songEntity(findSong(songSeq))
                .build());

        return 1;
    }

    @Override
    @Transactional
    public int insertSongIntoPlaylist(Long songSeq, Long playlistGroupSeq) {
        playlistTrackRepository.save(PlaylistTrackEntity.builder()
                .songEntity(findSong(songSeq))
                .playlistGroupEntity(findPlaylistGroup(playlistGroupSeq))
                .build());

        return 1;
    }

    @Override
    @Transactional
    public List<PlaylistDTO.PlaylistGroupResponseDTO> getAllPlaylists() {
        List<PlaylistGroupEntity> playlists = playlistGroupRepository.findByMember(getCurrentMember());
        if (playlists.isEmpty()) {
            throw new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST, HttpStatus.BAD_REQUEST);
        }

        return playlists.stream().map(PlaylistDTO.PlaylistGroupResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlaylistDTO.PlaylistGroupResponseDTO getPlaylistById(Long playlistGroupSeq) {
        return new PlaylistDTO.PlaylistGroupResponseDTO(findPlaylistGroup(playlistGroupSeq));
    }

    @Override
    @Transactional
    public int changePlaylist(Long playlistGroupSeq, PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO) {
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);
        playlistGroupEntity.setPlaylistName(playlistGroupRequestDTO.getPlaylistName());
        playlistGroupEntity.setIsOpen(playlistGroupRequestDTO.getIsOpen());

        playlistTagRepository.deleteAll(playlistTagRepository.findByPlaylistGroup(findPlaylistGroup(playlistGroupSeq)));

        if (playlistGroupRequestDTO.getTagList() != null && !playlistGroupRequestDTO.getTagList().isEmpty()) {
            for (String tag : playlistGroupRequestDTO.getTagList()) {
                TagEntity tagEntity = tagRepository.findByTagName(tag)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_TAG_ID, HttpStatus.BAD_REQUEST));

                playlistTagRepository.save(
                        PlaylistTagEntity.builder()
                                .tagEntity(tagEntity)
                                .playlistGroupEntity(findPlaylistGroup(playlistGroupSeq))
                                .build()
                );
            }
        }

        playlistGroupRepository.save(playlistGroupEntity);

        return 1;
    }

    @Override
    @Transactional
    public int deletePlaylistById(Long playlistGroupSeq) {
        playlistGroupRepository.deleteById(playlistGroupSeq);

        return 1;
    }

    @Override
    @Transactional
    public int deleteSongFromPlaylistById(Long playlistGroupSeq, Long songSeq) {
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);
        SongEntity songEntity = findSong(songSeq);

        PlaylistTrackEntity playlistTrackEntity = playlistTrackRepository
                .findByPlaylistGroupSeqAndSongSeq(playlistGroupEntity, songEntity)
                        .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_PLAYLIST_ID, HttpStatus.BAD_REQUEST));

        playlistTrackRepository.delete(playlistTrackEntity);

        if(playlistTrackRepository.findByPlaylistGroupSeq(playlistGroupEntity).isEmpty()) {
            deletePlaylistById(playlistGroupSeq);
        }

        return 1;
    }

//    @Override
//    @Transactional
//    public List<PlaylistDTO.PlaylistGroupResponseDTO> getOpenedPlaylists() {
//        List<PlaylistGroupEntity> roleDj = playlistGroupRepository.findByAuthority(CommonCode.MemberAuthority.ROLE_DJ);
//
//        return roleDj.stream().map(PlaylistDTO.PlaylistGroupResponseDTO::new).collect(Collectors.toList());
//    }

    @Override
    @Transactional
    public List<PlaylistDTO.PlaylistLikeResponseDTO> likedPlaylists() {
        List<PlaylistLikeEntity> playlistLikeEntityList = playlistLikeRepository.findByMember(getCurrentMember());
        if(playlistLikeEntityList.isEmpty()) {
            throw new BusinessException(ApiResponseCode.FAILED_LOAD_PLAYLIST_LIKE, HttpStatus.BAD_REQUEST);
        }

        return playlistLikeEntityList.stream().map(PlaylistDTO.PlaylistLikeResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int insertPlaylistLikeById(Long playlistGroupSeq) {
        playlistLikeRepository.save(PlaylistLikeEntity.builder()
                .playlistGroupEntity(findPlaylistGroup(playlistGroupSeq))
                .memberEntity(getCurrentMember())
                .build());

        return 1;
    }

    @Override
    @Transactional
    public int deletePlaylistLikeById(Long playlistGroupSeq) {
        MemberEntity memberEntity = getCurrentMember();
        PlaylistGroupEntity playlistGroupEntity = findPlaylistGroup(playlistGroupSeq);

        PlaylistLikeEntity playlistLikeEntity = playlistLikeRepository.liked(memberEntity, playlistGroupEntity)
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_PLAYLIST_LIKE_ID, HttpStatus.BAD_REQUEST));

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
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_PLAYLIST_ID, HttpStatus.BAD_REQUEST));
    }

    /**
     * songSeq 를 기준으로 SongEntity 를 찾아주는 메소드
     */
    private SongEntity findSong(Long songSeq) {
        return songRepository.findById(songSeq)
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_SONG_ID, HttpStatus.BAD_REQUEST));
    }
}
