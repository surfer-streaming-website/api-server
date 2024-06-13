package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "playlist_tag")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "playlist_tag_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "playlist_tag_seq" , name = "playlist_tag_seq")
    @Column(name = "playlist_tag_seq", nullable = false)
    private Long playlistTagSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_seq", referencedColumnName = "tag_seq", nullable = false)
    private TagEntity tagEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_group_seq", referencedColumnName = "playlist_group_seq", nullable = false)
    private PlaylistGroupEntity playlistGroupEntity;
}
