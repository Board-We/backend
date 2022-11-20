package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.dto.inner.BoardThemeResponseDto;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSelectResponseDto {
    private String boardName;
    private String boardDescription;
    private int memoCnt;
    private LocalDateTime openStartTime;
    private LocalDateTime openEndTime;
    private OpenType openType;
    private BoardStatus boardStatus;
    private BoardThemeResponseDto theme;
}
