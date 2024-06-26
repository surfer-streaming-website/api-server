package com.surfer.apiserver.api.search.controller;


import com.surfer.apiserver.api.search.dto.SearchRes;
import com.surfer.apiserver.api.search.service.SearchService;
import com.surfer.apiserver.common.util.AES256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("/{keyword}")
    public ResponseEntity<?> keywordSearch(@PathVariable String keyword) {

       System.out.println("검색어 = "+keyword);

       SearchRes searchRes= searchService.findKeyword(keyword);

       return new ResponseEntity<>(searchRes, HttpStatus.OK);
    }



}
