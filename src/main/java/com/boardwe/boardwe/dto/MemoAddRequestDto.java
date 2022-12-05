package com.boardwe.boardwe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoAddRequestDto {
    private String memoContent;
    private Long memoThemeId;
}
