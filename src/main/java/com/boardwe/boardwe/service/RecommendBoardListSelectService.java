package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;

public interface RecommendBoardListSelectService {
    List<BoardSearchResultResponseDto> getBoardList();
}
