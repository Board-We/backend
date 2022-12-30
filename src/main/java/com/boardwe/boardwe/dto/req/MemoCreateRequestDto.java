package com.boardwe.boardwe.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoCreateRequestDto {
    private String memoContent;
    private Long memoThemeId;
}
