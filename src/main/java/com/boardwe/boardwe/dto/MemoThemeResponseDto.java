package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoThemeResponseDto {
    private Long memoThemeId;
    private BackgroundType memoBackgroundType;
    private String memoBackground;
    private String memoTextColor;
}
