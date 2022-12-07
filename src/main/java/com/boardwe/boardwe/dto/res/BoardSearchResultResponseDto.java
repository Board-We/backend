package com.boardwe.boardwe.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSearchResultResponseDto {
    private String boardLink;
    private String boardName;
    private List<String> boardTags;
    private Integer boardViews;
    private BoardThemeSelectResponseDto theme;
}
