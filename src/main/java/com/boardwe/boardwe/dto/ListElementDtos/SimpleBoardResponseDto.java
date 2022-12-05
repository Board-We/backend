package com.boardwe.boardwe.dto.ListElementDtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimpleBoardResponseDto {
    private String boardLink;
    private String boardName;
    private List<String> boardTags;
    private int boardViews;
    private ThemeResponseDto theme;
}
