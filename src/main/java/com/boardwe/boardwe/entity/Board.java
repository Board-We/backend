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

import lombok.*;

@Entity
@Table(name = "BOARD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_theme_id", referencedColumnName = "board_theme_id")
    @NotNull
    private BoardTheme boardTheme;

    @Column(name = "board_name", length = 20)
    @NotNull
    private String name;

    @Column(name = "board_description", length = 50)
    private String description;

    @Column(name = "board_code", length = 36)
    @NotNull
    private String code;

    @Column(name = "board_writing_start_time")
    @NotNull
    private Timestamp writingStartTime;

    @Column(name = "board_writing_end_time")
    @NotNull
    private Timestamp writingEndTime;

    @Column(name = "board_open_start_time")
    @NotNull
    private Timestamp openStartTime;

    @Column(name = "board_open_end_time")
    @NotNull
    private Timestamp openEndTime;

    @Column(name = "board_password", length = 16)
    @NotNull
    private String password;
    
    @Column(name = "board_open_type", length = 10)
    @NotNull
    private String openType;

    @Column(name = "board_views")
    @NotNull
    private String views;

    @Builder
    public Board(BoardTheme boardTheme, String name, String description, String code, Timestamp writingStartTime, Timestamp writingEndTime, Timestamp openStartTime, Timestamp openEndTime, String password, String openType, String views) {
        this.boardTheme = boardTheme;
        this.name = name;
        this.description = description;
        this.code = code;
        this.writingStartTime = writingStartTime;
        this.writingEndTime = writingEndTime;
        this.openStartTime = openStartTime;
        this.openEndTime = openEndTime;
        this.password = password;
        this.openType = openType;
        this.views = views;
    }
}
