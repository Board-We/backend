package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardSearchService {
    Page<BoardReadResponseDto> searchBoardByTagWithPaging(String query, Pageable pageable);

    List<BoardReadResponseDto> selectHotBoards();

    List<BoardReadResponseDto> selectRecommendBoards();
}
