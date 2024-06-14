package com.surfer.apiserver.search.service;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.search.dto.SearchRes;

import java.util.List;

public interface SearchService {



   // List<SongEntity> findKeyword1(String keyword);

    SearchRes findKeyword(String keyword);
}


