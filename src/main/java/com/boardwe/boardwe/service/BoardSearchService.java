package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.dto.res.BoardSearchWithTimeResponseDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardSearchService {
    Page<BoardSearchResponseDto> searchBoardByTagWithPaging(String query, Pageable pageable);

    List<BoardSearchWithTimeResponseDto> selectHotBoards();

    List<BoardSearchWithTimeResponseDto> selectRecommendBoards();
}
