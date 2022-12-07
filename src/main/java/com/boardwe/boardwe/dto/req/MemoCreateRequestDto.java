package com.boardwe.boardwe.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoCreateRequestDto {
    private String memoContent;
    private Long memoThemeId;
}
