package com.surfer.apiserver.api.playlist.dto;

import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistTagEntity;
import com.surfer.apiserver.domain.database.entity.TagEntity;
import lombok.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDTO {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistGroupRequestDTO {
        private String playlistName;
        private int isOpen;
        private PlaylistTagRequestDTO tag;
//        private Authentication authentication;

        public PlaylistGroupEntity toPlaylistGroupEntity(PlaylistGroupRequestDTO playlistGroupRequest) {
        return playlistGroupRequest.getTag() == null ?
                PlaylistGroupEntity.builder()
                        .playlistName(playlistGroupRequest.getPlaylistName())
                        .isOpen(playlistGroupRequest.getIsOpen())
                        .build() :
                PlaylistGroupEntity.builder()
                        .playlistName(playlistGroupRequest.getPlaylistName())
                    .isOpen(playlistGroupRequest.getIsOpen())
                    .playlistTagEntities(playlistGroupRequest.getTag()
                            .toPlaylistTagEntityList(playlistGroupRequest.getTag()))
                    .build();


        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistGroupResponseDTO {
        private String playlistName;
        private int isOpen;
        private MemberResponseDTO nickname;
        private List<PlaylistTagResponseDTO> tag;
        private List<PlaylistTrackResponseDTO> track;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistTagRequestDTO {
        private TagRequestDTO tag;

        //리스트로 만들어주기
        public List<PlaylistTagEntity> toPlaylistTagEntityList(PlaylistTagRequestDTO playlistTagRequest) {
            List<PlaylistTagEntity> playlistTagEntities = new ArrayList<>();

            PlaylistTagEntity build = PlaylistTagEntity.builder()
                    .tagEntity(playlistTagRequest.getTag()
                            .toTagEntity(playlistTagRequest.getTag()))
                    .build();
            playlistTagEntities.add(build);

            return playlistTagEntities;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PlaylistTagResponseDTO {
        private TagResponseDTO tag;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagRequestDTO {
        private String tagName;

        public TagEntity toTagEntity(TagRequestDTO tagRequest) {
            return TagEntity.builder().tagName(tagRequest.getTagName()).build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TagResponseDTO {
        private String tagName;

        public TagResponseDTO(TagEntity tagEntity) {
            this.tagName = tagEntity.getTagName();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistTrackResponseDTO {
        private SongTestResponseDTO song;
        private String regDate;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SongTestResponseDTO {
        private String songName;
        private String artistName;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponseDTO {
        private String Nickname;
    }
}
