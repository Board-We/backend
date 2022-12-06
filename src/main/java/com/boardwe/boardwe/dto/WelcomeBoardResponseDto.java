package com.boardwe.boardwe.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WelcomeBoardResponseDto {
    private String boardName;
    private String boradDescription;
    private List<String> tags;
    private int memoCnt;
    private int boardViews;
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
