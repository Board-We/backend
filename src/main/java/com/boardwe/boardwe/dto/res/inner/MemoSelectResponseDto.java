package com.boardwe.boardwe.dto.res.inner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoSelectResponseDto {
    private Long memoThemeId;
    private String memoContent;
}