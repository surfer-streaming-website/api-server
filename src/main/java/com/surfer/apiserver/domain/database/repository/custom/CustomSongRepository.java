package com.surfer.apiserver.domain.database.repository.custom;

import com.surfer.apiserver.api.song.dto.GetAllSongsResponse;
import com.surfer.apiserver.api.song.dto.GetSongRankResponse;

import java.util.List;

public interface CustomSongRepository {
    List<GetSongRankResponse> getSongRank();
    List<GetAllSongsResponse> getAllSongs();
    List<GetAllSongsResponse> getAllSongsByGenre(String genre);
}
