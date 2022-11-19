package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardSelectResponseDto {
    private String boardName;
    private String boardDescription;
    private int memoCnt;
    private LocalDateTime openStartTime;
    private LocalDateTime openEndTime;
    private OpenType openType;
    private BoardStatus boardStatus;
}
