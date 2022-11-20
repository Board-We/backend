package com.boardwe.boardwe.dto.Inner;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardCreateThemeRequestDto {
    private String backgroundImage;
    private String backgroundImageName;
    private String backgroundColor;
    private String font;
    private List<MemoImagesTextColorSetsRequestDto> imageTextColorSet;
    private List<MemoBackgroundTextColorSetsRequestDto> backgroundTextColorSet;
}
