package com.boardwe.boardwe.dto.req.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoColorAndTextColorRequestDto {
    private String memoBackgroundColor;
    private String memoTextColor;
}
