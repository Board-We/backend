package com.boardwe.boardwe.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoSearchResponseDto {
    private BoardThemeSelectResponseDto theme;
    private List<MemoSelectResponseDto> memos;
}
