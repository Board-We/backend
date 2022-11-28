package com.boardwe.boardwe.dto.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoImagesTextColorSetsRequestDto {
    private String memoBackgroundImage;
    private String memoBackgroundImageName;
    private String memoTextColor;
}
