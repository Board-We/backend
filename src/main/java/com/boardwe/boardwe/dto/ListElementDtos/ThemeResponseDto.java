package com.boardwe.boardwe.dto.ListElementDtos;

import java.util.List;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ThemeResponseDto {
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
    private List<MemoThemeResponseDto> memoTheme;
}
