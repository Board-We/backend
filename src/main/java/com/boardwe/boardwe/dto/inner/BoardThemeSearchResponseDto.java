package com.boardwe.boardwe.dto.inner;

import com.boardwe.boardwe.type.BackgroundType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardThemeSearchResponseDto {
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
    private List<MemoThemesWithIdResponseDto> memoThemesWithId;
}
