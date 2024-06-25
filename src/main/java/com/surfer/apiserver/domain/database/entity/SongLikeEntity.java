package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "song_like")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_like_id_seq")
    @SequenceGenerator(name = "song_like_id_seq", sequenceName = "song_like_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_seq")
    private SongEntity song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
