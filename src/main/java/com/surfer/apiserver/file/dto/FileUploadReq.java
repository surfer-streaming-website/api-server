package com.surfer.apiserver.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FileUploadReq {

    private List<MultipartFileDTO> files1;
//    private Long userId;
//    private Long branchId;
//    private String message;
//
//    private String songTitle;
//    private List<String> songSingerNameList;

}