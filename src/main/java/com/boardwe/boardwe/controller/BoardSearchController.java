package com.boardwe.boardwe.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boardwe.boardwe.dto.BoardSearchDto;
import com.boardwe.boardwe.service.BoardSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardSearchController {
    private final BoardSearchService boardSearchService;

    @GetMapping("/board/search")
    public BoardSearchDto searchBoardsByTagValue(@RequestParam String query, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return BoardSearchDto.ok(boardSearchService.getBoardSearchResultPage(query, pageable));
    }
}
