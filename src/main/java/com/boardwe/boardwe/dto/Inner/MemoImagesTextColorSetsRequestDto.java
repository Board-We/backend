package com.boardwe.boardwe.dto.Inner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoImagesTextColorSetsRequestDto {
    private String memoBackgroundImage;
    private String memoBackgroundImageName;
    private String textColor;
}
