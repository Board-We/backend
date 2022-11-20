package com.boardwe.boardwe.dto.inner;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardThemeResponseDto {
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
    private List<MemoThemeResponseDto> memoThemesWithId;
}
