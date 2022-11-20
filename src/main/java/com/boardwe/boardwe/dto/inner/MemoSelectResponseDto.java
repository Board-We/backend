package com.boardwe.boardwe.dto.inner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoSelectResponseDto {
    private Long memoThemeId;
    private String memoContent;
}
