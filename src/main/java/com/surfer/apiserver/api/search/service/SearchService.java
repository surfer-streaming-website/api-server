package com.surfer.apiserver.api.search.service;

import com.surfer.apiserver.api.search.dto.SearchRes;

public interface SearchService {



   // List<SongEntity> findKeyword1(String keyword);

    SearchRes findKeyword(String keyword);
}


