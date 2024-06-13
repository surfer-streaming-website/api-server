package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "tag_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "tag_seq" , name = "tag_seq")
    @Column(name = "tag_seq", nullable = false)
    private Long tagSeq;
    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
