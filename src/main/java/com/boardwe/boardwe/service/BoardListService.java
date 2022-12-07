package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;

public interface BoardListService {
    List<BoardSearchResultResponseDto> selectHotBoards();
    List<BoardSearchResultResponseDto> selectRecommendBoards();
}
