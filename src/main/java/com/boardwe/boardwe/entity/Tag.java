package com.boardwe.boardwe.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TAG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @NotNull
    private Board board;

    @Column(name = "tag_value", length = 20)
    @NotNull
    private String value;

    @Builder
    public Tag(Board board, String value) {
        this.board = board;
        this.value = value;
    }
}
