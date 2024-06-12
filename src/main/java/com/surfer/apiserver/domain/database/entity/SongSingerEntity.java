package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "song_singer")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongSingerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_singer_seq")
    @SequenceGenerator(name = "song_singer_seq", sequenceName = "song_singer_seq", allocationSize = 1)
    private String songSingerSeq;

    @Column(name = "song_singer_name")
    private String songSingerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_seq")
    @JsonIgnore
    private SongEntity songEntity;
}
