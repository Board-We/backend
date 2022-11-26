package com.boardwe.boardwe.entity;

import com.boardwe.boardwe.type.OpenType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_theme_id")
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
    private LocalDateTime writingStartTime;

    @Column(name = "board_writing_end_time")
    @NotNull
    private LocalDateTime writingEndTime;

    @Column(name = "board_open_start_time")
    @NotNull
    private LocalDateTime openStartTime;

    @Column(name = "board_open_end_time")
    @NotNull
    private LocalDateTime openEndTime;

    @Column(name = "board_password", length = 16)
    @NotNull
    private String password;
    
    @Column(name = "board_open_type", length = 10)
    @Enumerated(EnumType.STRING)
    @NotNull
    private OpenType openType;

    @Column(name = "board_views")
    @NotNull
    private Integer views;

    @Builder
    public Board(BoardTheme boardTheme, String name, String description, String code, LocalDateTime writingStartTime, LocalDateTime writingEndTime, LocalDateTime openStartTime, LocalDateTime openEndTime, String password, OpenType openType, Integer views) {
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
