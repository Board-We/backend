package com.boardwe.boardwe.dto.req.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardThemeCreateRequestDto {
    private String boardBackgroundImage;
    private String boardBackgroundImageName;
    private String boardBackgroundColor;
    private String boardFont;
    private List<MemoImageAndTextColorRequestDto> memoImageTextColorSets;
    private List<MemoColorAndTextColorRequestDto> memoBackgroundTextColorSets;
}
