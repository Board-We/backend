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
public class BoardTheme {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_theme_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_category_id")
    @NotNull
    private ThemeCategory themeCategory;

    @Column(name = "board_theme_name")
    @NotNull
    private String name;

    @Column(name="board_theme_background_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private BackgroundType backgroundType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_theme_background_image_id")
    private ImageInfo backgroundImageInfo;


    @Column(name="board_theme_background_color")
    private String backgroundColor;

    @Column(name="board_theme_font")
    private String font;

    @Builder
    public BoardTheme(ThemeCategory themeCategory, String name, BackgroundType backgroundType, ImageInfo backgroundImageInfo, String backgroundColor, String font) {
        this.themeCategory = themeCategory;
        this.name = name;
        this.backgroundType = backgroundType;
        this.backgroundImageInfo = backgroundImageInfo;
        this.backgroundColor = backgroundColor;
        this.font = font;
    }
}
