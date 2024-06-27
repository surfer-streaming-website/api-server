package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "song")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_id_seq")
    @SequenceGenerator(name = "song_id_seq", allocationSize = 1, sequenceName = "song_id_seq")
    @Column(name = "song_seq", updatable = false)
    private Long songSeq;

    @Column(name="song_title", nullable = false)
    private String songTitle;

    @Column(name="song_number", nullable = false)
    private int songNumber;

    @Column(name="lyrics")
    private String lyrics;

    @Column(name="total_played_count")
    private int totalPlayedCount=0;

    @Column(name="recently_played_count")
    private int recentlyPlayedCount=0;

    @Column(name="genre", nullable = false)
    private String genre;

    @Column(name="song_state")
    private Boolean songState;

    @Column(name = "sound_source_name")
    private String soundSourceName;

    @Column(name = "producer")
    private String producer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="album_seq")
    @JsonIgnore
    private AlbumEntity albumEntity;

    @OneToMany(mappedBy = "songEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SongReplyEntity> songReplyEntities;

    @OneToMany(mappedBy = "songEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SongSingerEntity> songSingerEntities;

}

