package com.surfer.apiserver.domain.database.entity;

import com.surfer.apiserver.common.constant.CommonCode.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq")
    @SequenceGenerator(name = "member_id_seq", sequenceName = "member_id_seq", allocationSize = 1)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expired_at")
    private Long refreshTokenExpiredAt;

    @Column(name = "register_at")
    @CreationTimestamp
    private Date registerAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberAuthorityEntity> memberAuthorityEntities;

    @Builder
    public MemberEntity(Long memberId, String email, String password, String nickname, String name, MemberStatus status, String refreshToken, Long refreshTokenExpiredAt, Date registerAt, Date updateAt) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.status = status;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
        this.registerAt = registerAt;
        this.updateAt = updateAt;
    }
}
