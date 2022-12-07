package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;
import com.boardwe.boardwe.service.BoardSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSearchController {
    private final BoardSearchService boardSearchService;

    @GetMapping("/board/search")
    public ResponseEntity<Page<BoardSearchResultResponseDto>> searchBoardsByTagValue(@RequestParam String query, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(boardSearchService.getBoardSearchResultPage(query, pageable));
    }
}
