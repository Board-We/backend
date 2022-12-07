package com.boardwe.boardwe.dto.req.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoImageAndTextColorRequestDto {
    private String memoBackgroundImage;
    private String memoBackgroundImageName;
    private String memoTextColor;
}
