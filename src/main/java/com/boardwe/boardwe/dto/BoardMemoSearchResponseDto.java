package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.dto.inner.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.inner.BoardThemeSearchResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMemoSearchResponseDto {
    private BoardThemeSearchResponseDto theme;
    private List<MemoSearchResponseDto> memos;
}
