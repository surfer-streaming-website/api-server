package com.surfer.apiserver.api.playlist.dto;

import com.surfer.apiserver.domain.database.entity.*;
import lombok.*;

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
        private List<String> tagList;
        private List<String> deleteTagList;


        public PlaylistGroupEntity toPlaylistGroupEntityWithOutTag(PlaylistGroupRequestDTO playlistGroupRequest) {

            return new PlaylistGroupEntity().builder()
                    .isOpen(playlistGroupRequest.getIsOpen())
                    .playlistName(playlistGroupRequest.getPlaylistName())
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

        public PlaylistGroupResponseDTO(PlaylistGroupEntity playlistGroupEntity) {
            this.playlistName = playlistGroupEntity.getPlaylistName();
            this.isOpen = playlistGroupEntity.getIsOpen();
            this.nickname = new MemberResponseDTO(playlistGroupEntity.getMemberEntity().getNickname());

            List<PlaylistTagResponseDTO> playlistTagResponseDTOList = new ArrayList<>();
            for (PlaylistTagEntity playlistTagEntity : playlistGroupEntity.getPlaylistTagEntities()) {
                PlaylistTagResponseDTO playlistTagResponseDTO = new PlaylistTagResponseDTO(playlistTagEntity);
                playlistTagResponseDTOList.add(playlistTagResponseDTO);
            }
            this.tag = playlistTagResponseDTOList;

            List<PlaylistTrackResponseDTO> playlistTrackResponseDTOList = new ArrayList<>();
            for (PlaylistTrackEntity playlistTrackEntity : playlistGroupEntity.getPlaylistTrackEntities()) {
                PlaylistTrackResponseDTO playlistTrackResponseDTO = new PlaylistTrackResponseDTO(playlistTrackEntity);
                playlistTrackResponseDTOList.add(playlistTrackResponseDTO);
            }
            this.track = playlistTrackResponseDTOList;
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

        public PlaylistTagResponseDTO(PlaylistTagEntity playlistTagEntity) {
            this.tag = new TagResponseDTO(playlistTagEntity.getTagEntity());
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
        private SongResponseDTO song;
        private String regDate;

        public PlaylistTrackResponseDTO(PlaylistTrackEntity playlistTrackEntity) {
            this.song = new SongResponseDTO(playlistTrackEntity.getSongEntity());
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SongResponseDTO {
        private String songName;
        private List<SongSingerEntity> artist;

        public SongResponseDTO(SongEntity songEntity) {
            this.songName = songEntity.getSongTitle();
            this.artist = songEntity.getSongSingerEntityList();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponseDTO {
        private String Nickname;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaylistLikeResponseDTO {
        private PlaylistGroupResponseDTO playlistGroup;

        public PlaylistLikeResponseDTO(PlaylistLikeEntity playlistLikeEntity) {
            this.playlistGroup = new PlaylistGroupResponseDTO(playlistLikeEntity.getPlaylistGroupEntity());
        }
    }
}
