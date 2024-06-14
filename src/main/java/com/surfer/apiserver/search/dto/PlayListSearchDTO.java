package com.surfer.apiserver.search.dto;


import lombok.Getter;

@Getter
public class PlayListSearchDTO {

    private String playListName;
    private String albumImage;
    private String memberName;

    //플레이리스트 3
    public PlayListSearchDTO(String playListName,String albumImage,String memberName) {
        this.playListName = playListName;
        this.albumImage = albumImage;
        this.memberName = memberName;
    }

}
