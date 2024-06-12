package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "album_singer")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumSingerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_singer_seq")
    @SequenceGenerator(name = "album_singer_seq", sequenceName = "album_singer_seq", allocationSize = 1)
    private Long albumSingerSeq;

    @Column(name = "album_singer_name")
    private String albumSingerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_seq")
    @JsonIgnore
    private AlbumEntity albumEntity;
}
