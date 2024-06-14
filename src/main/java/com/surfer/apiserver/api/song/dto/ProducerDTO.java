package com.surfer.apiserver.api.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProducerDTO {
    List<String> composerList;
    List<String> lyricistList;
    List<String> arrangerList;
}
