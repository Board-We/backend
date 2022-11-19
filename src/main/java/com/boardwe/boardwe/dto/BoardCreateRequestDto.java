package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.type.OpenType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BoardCreateRequestDto {
    private String name;
    private String description;
    private List<String> tags;
    private LocalDateTime writingStartTime;
    private LocalDateTime writingEndTime;
    private LocalDateTime openStartTime;
    private LocalDateTime openEndTime;
    private String password;
    private OpenType openType;
    private Long boardThemeId;
}
