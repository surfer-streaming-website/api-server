package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "authority")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_id_seq")
    @SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public AuthorityEntity(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public AuthorityEntity update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
