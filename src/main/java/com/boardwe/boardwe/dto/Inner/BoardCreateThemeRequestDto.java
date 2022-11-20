package com.boardwe.boardwe.dto.Inner;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardCreateThemeRequestDto {
    private String backgroundImage;
    private String backgroundColor;
    private String font;
    private List<MemoImagesTextColorSetsDto> imageTextColorSet;
    private List<MemoBackgroundTextColorSetsDto> backgroundTextColorSet;
}
