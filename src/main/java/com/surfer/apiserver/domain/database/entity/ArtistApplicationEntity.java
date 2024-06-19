package com.surfer.apiserver.domain.database.entity;

import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.common.constant.CommonCode.ArtistApplicationStatus;
import com.surfer.apiserver.common.constant.CommonCode.LocationType;
import com.surfer.apiserver.common.constant.CommonCode.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "artist_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class ArtistApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_id_seq")
    @SequenceGenerator(name = "application_id_seq", sequenceName = "application_id_seq", allocationSize = 1)
    @Column(name = "application_id", updatable = false)
    private Long application_id;
    @Column(name = "location_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    @Column(name = "sector", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sector sector;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArtistApplicationStatus status;
    @Column(name = "copyrightName", nullable = false)
    private String copyrightName;
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "author_name")
    private String authorName;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private MemberEntity member;
    @Column(name = "create_at", nullable = false)
    @CreationTimestamp
    private Date createAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;

    @Builder
    public ArtistApplicationEntity(Long application_id, LocationType locationType, Sector sector, ArtistApplicationStatus status, String copyrightName, String albumName, String artistName, String authorName, MemberEntity member, Date createAt, Date updateAt) {
        this.application_id = application_id;
        this.locationType = locationType;
        this.sector = sector;
        this.status = status;
        this.copyrightName = copyrightName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.authorName = authorName;
        this.member = member;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
