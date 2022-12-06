package com.boardwe.boardwe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boardwe.boardwe.dto.ListElementDtos.SimpleBoardResponseDto;

public interface BoardSearchService {
    Page<SimpleBoardResponseDto> getBoardSearchResultPage(String query, Pageable pageable);
}
