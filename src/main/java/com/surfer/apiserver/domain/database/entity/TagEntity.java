package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tag")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "tag_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "tag_id_seq" , name = "tag_id_seq")
    @Column(name = "tag_Id", nullable = false)
    private Long tagId;
    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
