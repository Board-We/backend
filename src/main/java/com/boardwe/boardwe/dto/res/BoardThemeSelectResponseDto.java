package com.boardwe.boardwe.dto.res;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardThemeSelectResponseDto {
    private Long id;
    private String name;
    private String category;
    private Integer useCnt;
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
    private List<MemoThemeSelectResponseDto> memoThemes;
}
