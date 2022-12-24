package com.boardwe.boardwe.entity;

import com.boardwe.boardwe.type.BackgroundType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_theme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="board_theme_id",
            foreignKey = @ForeignKey(name="FK_board_theme_TO_memo_theme_1")
    )
    @NotNull
    private BoardTheme boardTheme;

    @Enumerated(EnumType.STRING)
    @Column(name = "memo_theme_background_type")
    @NotNull
    private BackgroundType backgroundType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="memo_theme_background_image_id",
            foreignKey = @ForeignKey(name="FK_image_info_TO_memo_theme_1")
    )
    private ImageInfo backgroundImageInfo;

    @Column(name = "memo_theme_background_color")
    private String backgroundColor;

    @Column(name = "memo_theme_text_color")
    private String textColor;

    @Builder
    public MemoTheme(BoardTheme boardTheme, BackgroundType backgroundType, ImageInfo backgroundImageInfo, String backgroundColor, String textColor) {
        this.boardTheme = boardTheme;
        this.backgroundType = backgroundType;
        this.backgroundImageInfo = backgroundImageInfo;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }
}
