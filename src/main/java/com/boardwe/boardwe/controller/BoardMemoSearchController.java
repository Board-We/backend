package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardMemoSearchResponseDto;
import com.boardwe.boardwe.service.BoardMemoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardMemoSearchController {

    private final BoardMemoSearchService boardMemoSearchService;

    @GetMapping("/board/{boardCode}/memo/search")
    public ResponseEntity<BoardMemoSearchResponseDto> searchMemo(@PathVariable String boardCode,
                                  @RequestParam String query){
        return ResponseEntity.ok(boardMemoSearchService.searchMemo(boardCode,query));
    }

}
