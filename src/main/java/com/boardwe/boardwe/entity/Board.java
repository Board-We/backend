package com.boardwe.boardwe.entity;

import java.sql.Timestamp;

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
@Table(name = "BOARD")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @ManyToOne
    @JoinColumn(name = "board_theme_id", referencedColumnName = "board_theme_id")
    @NotNull
    private long boardThemeId;

    @Column(name = "board_name", length = 20)
    @NotNull
    private String boardName;

    @Column(name = "board_description", length = 50)
    private String boardDescription;

    @Column(name = "board_code", length = 36)
    @NotNull
    private String boardCode;

    @Column(name = "board_writing_start_time")
    @NotNull
    private Timestamp boardWritingStartTime;

    @Column(name = "board_writing_end_time")
    @NotNull
    private Timestamp boardWritingEndTime;

    @Column(name = "board_open_start_time")
    @NotNull
    private Timestamp boardOpenStartTime;

    @Column(name = "board_open_end_time")
    @NotNull
    private Timestamp boardOpenEndTime;

    @Column(name = "board_password", length = 16)
    @NotNull
    private String boardPassword;
    
    @Column(name = "board_open_type", length = 10)
    @NotNull
    private String boardOpenType;

    @Column(name = "board_views")
    @NotNull
    private String boardViews;
}
