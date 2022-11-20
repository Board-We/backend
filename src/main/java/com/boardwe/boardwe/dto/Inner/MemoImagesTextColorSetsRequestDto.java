package com.boardwe.boardwe.dto.Inner;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
