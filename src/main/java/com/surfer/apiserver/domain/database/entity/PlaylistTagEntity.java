package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist_tag")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "playlist_tag_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "playlist_tag_id_seq" , name = "playlist_tag_id_seq")
    @Column(name = "playlist_tag_id", nullable = false)
    private Long playlistTagSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false)
    private TagEntity tagEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_group_id", referencedColumnName = "playlist_group_id", nullable = false)
    private PlaylistGroupEntity playlistGroupEntity;
}
