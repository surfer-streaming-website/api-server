package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "song_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "song_seq" , name = "song_seq")
    @Column(name = "song_seq", nullable = false)
    private Long songSeq;
    @Column(name = "song_name", nullable = false)
    private String songName;
    @Column(name = "singer", nullable = false)
    private String singer;

    @OneToMany(mappedBy = "songEntity" , cascade = CascadeType.ALL)
    private List<PlaylistTrackEntity> playlistTrackEntities;
}
