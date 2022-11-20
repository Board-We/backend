package com.boardwe.boardwe.dto.Inner;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateThemeRequestDto {
    private String boardBackgroundImage;
    private String boardBackgroundImageName;
    private String boardBackgroundColor;
    private String boardFont;
    private List<MemoImagesTextColorSetsRequestDto> memoImageTextColorSets;
    private List<MemoBackgroundTextColorSetsRequestDto> memoBackgroundTextColorSets;
}
