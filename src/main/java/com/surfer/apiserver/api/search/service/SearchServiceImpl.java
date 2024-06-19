package com.surfer.apiserver.api.search.service;

import com.surfer.apiserver.api.search.dto.*;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    private final SongRepository songRepository;

    @Autowired
    public SearchServiceImpl(SongRepository songRepository) {

        this.songRepository = songRepository;
    }




    @Override
    public SearchRes findKeyword(String keyword) {

        //키워드가 제대로 들어왔는지 확인
        if(keyword == null || keyword.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_KEYWORD, HttpStatus.BAD_REQUEST);
        }
        List<SongSearchDTO> song = songRepository.findKeywordSong(keyword);
        List<AlbumSearchDTO> album = songRepository.findKeywordAlbum(keyword);
        List<PlaylistSearchDTO> playlist = songRepository.findKeywordPlaylist(keyword);
        List<LyricsSearchDTO> lyrics = songRepository.findKeywordLyrics(keyword);


        SearchRes searchRes = new SearchRes(song,album,playlist,lyrics);

        return searchRes;
    }


}
