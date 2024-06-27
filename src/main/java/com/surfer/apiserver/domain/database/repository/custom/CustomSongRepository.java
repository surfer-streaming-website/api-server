package com.surfer.apiserver.domain.database.repository.custom;

import com.surfer.apiserver.api.song.dto.GetSongRankResponse;

import java.util.List;

public interface CustomSongRepository {
    List<GetSongRankResponse> getSongRank();
}
