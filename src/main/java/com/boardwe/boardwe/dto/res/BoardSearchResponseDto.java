package com.boardwe.boardwe.dto.res;

import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSearchResponseDto {
    private String boardLink;
    private String boardName;
    private List<String> boardTags;
    private LocalDateTime writingStartTime;
    private LocalDateTime writingEndTime;
    private LocalDateTime openStartTime;
    private Integer boardViews;
    private BackgroundType boardBackgroundType;
    private String boardBackground;
    private String boardFont;
}
