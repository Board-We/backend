package com.boardwe.boardwe.dto.res;

import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WelcomeBoardResponseDto {
    private String boardName;
    private String boardDescription;
    private List<String> tags;
    private Integer memoCnt;
    private Integer boardViews;
    private LocalDateTime writingStartTime;
    private LocalDateTime writingEndTime;
    private LocalDateTime openStartTime;
    private LocalDateTime openEndTime;
    private OpenType openType;
    private BoardStatus boardStatus;
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
}
