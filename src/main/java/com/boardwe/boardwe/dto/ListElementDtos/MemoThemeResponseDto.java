package com.boardwe.boardwe.dto.ListElementDtos;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemoThemeResponseDto {
    private BackgroundType memoBackgroundType;
    private String memoBackground;
    private String memoTextColor;
}
