package com.boardwe.boardwe.dto.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoSearchResponseDto {
    private Long memoThemeId;
    private String memoContent;
}