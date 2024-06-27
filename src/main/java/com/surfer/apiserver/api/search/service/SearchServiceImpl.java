package com.surfer.apiserver.api.search.service;

import com.surfer.apiserver.api.album.service.impl.AlbumServiceImpl;
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
    private final AlbumServiceImpl albumServiceImpl;

    @Autowired
    public SearchServiceImpl(SongRepository songRepository, AlbumServiceImpl albumServiceImpl) {

        this.songRepository = songRepository;
        this.albumServiceImpl = albumServiceImpl;
    }




    @Override
    public SearchRes findKeyword(String keyword) {

        //키워드가 제대로 들어왔는지 확인
        if(keyword == null || keyword.isEmpty()) {
            throw new BusinessException(ApiResponseCode.INVALID_KEYWORD, HttpStatus.BAD_REQUEST);
        }
        List<SongSearchDTO> song = songRepository.findKeywordSong(keyword);
        for(SongSearchDTO songSearchDTO : song) {
            String newAlbumImage = String.valueOf(albumServiceImpl.generateAlbumImgFileUrl(songSearchDTO.getAlbumImage()));
            songSearchDTO.setAlbumImage(newAlbumImage);
        }

        List<AlbumSearchDTO> album = songRepository.findKeywordAlbum(keyword);
        for(AlbumSearchDTO albumSearchDTO : album) {
            String newAlbumImage = String.valueOf(albumServiceImpl.generateAlbumImgFileUrl(albumSearchDTO.getAlbumImage()));
            albumSearchDTO.setAlbumImage(newAlbumImage);
        }
        List<PlaylistSearchDTO> playlist = songRepository.findKeywordPlaylist(keyword);
        for (PlaylistSearchDTO playlistSearchDTO : playlist) {
            String newAlbumImage = String.valueOf(albumServiceImpl.generateAlbumImgFileUrl(playlistSearchDTO.getAlbumImage()));
            playlistSearchDTO.setAlbumImage(newAlbumImage);
        }

        List<LyricsSearchDTO> lyrics = songRepository.findKeywordLyrics(keyword);
        for (LyricsSearchDTO lyricsSearchDTO : lyrics) {
            String newAlbumImage = String.valueOf(albumServiceImpl.generateAlbumImgFileUrl(lyricsSearchDTO.getAlbumImage()));
            lyricsSearchDTO.setAlbumImage(newAlbumImage);
        }

        SearchRes searchRes = new SearchRes(song,album,playlist,lyrics);

        return searchRes;
    }


}
