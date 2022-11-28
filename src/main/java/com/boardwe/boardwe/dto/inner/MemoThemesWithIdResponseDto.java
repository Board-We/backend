package com.boardwe.boardwe.dto.inner;

import com.boardwe.boardwe.type.BackgroundType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoThemesWithIdResponseDto {
    private Long memoThemeId;
    private BackgroundType memoBackgroundType;
    private String memoBackground;
    private String memoTextColor;
}
