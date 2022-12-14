package com.boardwe.boardwe.dto.req;

import com.boardwe.boardwe.dto.req.inner.MemoThemesCreateRequestDto;
import com.boardwe.boardwe.type.OpenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCreateRequestDto {
    private String boardName;
    private String boardDescription;
    private List<String> tags;
    private LocalDateTime writingStartTime;
    private LocalDateTime writingEndTime;
    private LocalDateTime openStartTime;
    private LocalDateTime openEndTime;
    private String password;
    private OpenType openType;
    private Long boardThemeId;
    private String boardBackground;
    private String boardFont;
    private MemoThemesCreateRequestDto memoThemes;
}
