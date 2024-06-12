package com.surfer.apiserver.domain.database.entity;

import com.surfer.apiserver.common.constant.CommonCode.MemberAuthority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "member_authority")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class MemberAuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_id_seq")
    @SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", allocationSize = 1)
    @Column(name = "authority_id", updatable = false)
    private Long authorityId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberAuthority authority;

    @Column(name = "create_at", nullable = false)
    @CreationTimestamp
    private Date createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;

    @Builder
    public MemberAuthorityEntity(Long authorityId, MemberEntity member, MemberAuthority authority, Date createAt, Date updateAt) {
        this.authorityId = authorityId;
        this.member = member;
        this.authority = authority;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
