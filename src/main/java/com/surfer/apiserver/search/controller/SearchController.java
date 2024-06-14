package com.surfer.apiserver.search.controller;


import com.surfer.apiserver.search.dto.SearchRes;
import com.surfer.apiserver.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/song")
public class SearchController {

    private SearchService searchService;


    @GetMapping("/{keyword}")
    public ResponseEntity<?> keywordSearch(@PathVariable String keyword) {


       SearchRes searchRes= searchService.findKeyword(keyword);

       return new ResponseEntity<>(searchRes, HttpStatus.OK);
    }




    @PostMapping("/list")

    public String list(){

//        songService.findKeyword();

        return "list ok";
    }

}
