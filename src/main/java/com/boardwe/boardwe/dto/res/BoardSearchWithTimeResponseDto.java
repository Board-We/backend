package com.boardwe.boardwe.dto.res;

import java.time.LocalDateTime;
import java.util.List;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSearchWithTimeResponseDto {
    private String boardLink;
    private String boardName;
    private List<String> boardTags;
    private LocalDateTime writingStartTime;
    private LocalDateTime openStartTime;
    private Integer boardViews;
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
}
