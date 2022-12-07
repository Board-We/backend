package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.BoardMemoSearchResponseDto;

public interface BoardMemoSearchService {
    BoardMemoSearchResponseDto searchMemo(String boardCode, String query);
}
