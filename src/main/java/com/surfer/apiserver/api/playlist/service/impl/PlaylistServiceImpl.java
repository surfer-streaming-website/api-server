package com.surfer.apiserver.api.playlist.service.impl;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistLikeEntity;
import com.surfer.apiserver.domain.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int createNewPlaylist(PlaylistDTO.PlaylistGroupRequestDTO playlistGroupRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberEntity member = memberRepository.findByMemberId(
                Long.parseLong(AES256Util.decrypt(authentication.getName()))
        )
                .orElseThrow(()->new BusinessException(ApiResponseCode.INVALID_API_ACCESS_TOKEN, HttpStatus.BAD_REQUEST));

        PlaylistGroupEntity playlistGroupEntity =
                playlistGroupRequestDTO.toPlaylistGroupEntity(playlistGroupRequestDTO);
        playlistGroupEntity.setMemberEntity(member);
        playlistGroupRepository.save(playlistGroupEntity);

        return 1;
    }
}
