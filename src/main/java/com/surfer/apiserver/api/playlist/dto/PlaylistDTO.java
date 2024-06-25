package com.surfer.apiserver.api.playlist.dto;

import com.surfer.apiserver.domain.database.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistDTO {
    @Getter
    @Setter
    public static class PlaylistGroupRequestDTO {
        private String playlistName;
        private int isOpen;
        private List<String> tagList;

        public PlaylistGroupEntity toPlaylistGroupEntityWithOutTag(PlaylistGroupRequestDTO playlistGroupRequest) {
            return new PlaylistGroupEntity().builder()
                    .isOpen(playlistGroupRequest.getIsOpen())
                    .playlistName(playlistGroupRequest.getPlaylistName())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class PlaylistGroupResponseDTO {
        private Long playlistId;
        private String playlistImage;
        private String playlistName;
        private String nickname;
        private List<PlaylistTagResponseDTO> tag;
        private List<PlaylistTrackResponseDTO> track;

        public PlaylistGroupResponseDTO(PlaylistGroupEntity playlistGroupEntity) {
            this.playlistId = playlistGroupEntity.getPlaylistGroupSeq();
            this.playlistName = playlistGroupEntity.getPlaylistName();
            this.nickname = playlistGroupEntity.getMemberEntity().getNickname();

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
            this.track = playlistTrackResponseDTOList.stream()
                    .sorted(Comparator.comparing(PlaylistTrackResponseDTO::getRegDate)).collect(Collectors.toList());

            this.playlistImage = playlistTrackResponseDTOList.get(0).getSong().getAlbumImage();
        }
    }

    @Getter
    @Setter
    public static class PlaylistTagResponseDTO {
        private String tag;

        public PlaylistTagResponseDTO(PlaylistTagEntity playlistTagEntity) {
            this.tag = playlistTagEntity.getTagEntity().getTagName();
        }
    }

    @Getter
    @Setter
    public static class PlaylistTrackResponseDTO {
        private Long trackId;
        private SongResponseDTO song;
        private LocalDateTime regDate;

        public PlaylistTrackResponseDTO(PlaylistTrackEntity playlistTrackEntity) {
            this.trackId = playlistTrackEntity.getPlaylistTrackSeq();
            this.song = new SongResponseDTO(playlistTrackEntity.getSongEntity());
            this.regDate = playlistTrackEntity.getRegDate();
        }
    }

    @Getter
    @Setter
    public static class SongResponseDTO {
        private Long songId;
        private String albumImage;
        private String songName;
        private List<SongSingerResponseDTO> artist;

        public SongResponseDTO(SongEntity songEntity) {
            this.songId = songEntity.getSongSeq();
            this.songName = songEntity.getSongTitle();
            this.albumImage = songEntity.getAlbumEntity().getAlbumImage();

            List<SongSingerResponseDTO> songSingerResponseDTOList = new ArrayList<>();
            for (SongSingerEntity songSingerEntity : songEntity.getSongSingerEntities()) {
                SongSingerResponseDTO songSingerResponseDTO = new SongSingerResponseDTO(songSingerEntity);
                songSingerResponseDTOList.add(songSingerResponseDTO);
            }
            this.artist = songSingerResponseDTOList;
        }
    }

    @Getter
    @Setter
    public static class SongSingerResponseDTO {
        private String singer;

        public SongSingerResponseDTO(SongSingerEntity songSingerEntity) {
            this.singer = songSingerEntity.getSongSingerName();
        }
    }

    @Getter
    @Setter
    public static class PlaylistLikeResponseDTO {
        private PlaylistGroupResponseDTO playlistGroup;

        public PlaylistLikeResponseDTO(PlaylistLikeEntity playlistLikeEntity) {
            this.playlistGroup = new PlaylistGroupResponseDTO(playlistLikeEntity.getPlaylistGroupEntity());
        }
    }
}
