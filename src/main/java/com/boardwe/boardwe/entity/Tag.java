package com.boardwe.boardwe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    @NotNull
    private long boardId;

    @Column(name = "tag_value", length = 20)
    @NotNull
    private String tagValue;
}
