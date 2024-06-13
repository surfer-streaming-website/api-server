package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "playlist_group_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "playlist_group_seq" , name = "playlist_group_seq")
    @Column(name = "playlist_group_seq", nullable = false)
    private Long playlistGroupSeq;
    @Column(name = "is_open", nullable = false)
    private int isOpen;
    @Column(name = "playlist_name", nullable = false)
    private String playlistName;
    @CreationTimestamp
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "playlistGroup", cascade = CascadeType.ALL)
    private List<PlaylistTrackEntity> playlistTrackEntities;

    @OneToMany(mappedBy = "playlistGroupEntity", cascade = CascadeType.ALL)
    private List<PlaylistTagEntity> playlistTagEntities;
}
