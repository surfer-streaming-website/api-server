package com.surfer.apiserver.search.service;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import com.surfer.apiserver.search.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("여기까지? = " + keyword);
        List<SongSearchDTO> song = songRepository.findKeywordSong(keyword);
        List<AlbumSearchDTO> album = songRepository.findKeywordAlbum(keyword);
        List<PlayListSearchDTO> playList = songRepository.findKeywordPlayList(keyword);
        List<LyricsSearchDTO> lyrics = songRepository.findKeywordLyrics(keyword);


        SearchRes searchRes = new SearchRes(song,album,playList,lyrics);

        return searchRes;
    }


}
