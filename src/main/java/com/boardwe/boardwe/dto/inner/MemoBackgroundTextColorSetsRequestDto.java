package com.boardwe.boardwe.dto.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoBackgroundTextColorSetsRequestDto {
    private String memoBackgroundColor;
    private String memoTextColor;
}
