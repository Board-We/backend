package com.boardwe.boardwe.dto.res;

import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
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
public class BoardSearchResponseDto {
    private String boardLink;
    private String boardName;
    private List<String> boardTags;
    private Integer boardViews;
    private BoardThemeSelectResponseDto theme;
    private List<MemoSelectResponseDto> memos;
}
