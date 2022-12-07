package com.boardwe.boardwe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;

public interface BoardSearchService {
    Page<BoardSearchResultResponseDto> getBoardSearchResultPage(String query, Pageable pageable);
}
