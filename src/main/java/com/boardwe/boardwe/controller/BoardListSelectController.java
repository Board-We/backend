package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;
import com.boardwe.boardwe.service.HotBoardListSelectService;
import com.boardwe.boardwe.service.RecommendBoardListSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardListSelectController {
    private final HotBoardListSelectService hotBoardListSelectService;
    private final RecommendBoardListSelectService recommendBoardListSelectService;

    @GetMapping("/boards/hot")
    public ResponseEntity<List<BoardSearchResultResponseDto>> getHotBoards(){
        return ResponseEntity.ok(hotBoardListSelectService.getBoardList());
    }

    @GetMapping("/boards/recommend")
    public ResponseEntity<List<BoardSearchResultResponseDto>> getRecommendBoards(){
        return ResponseEntity.ok(recommendBoardListSelectService.getBoardList());
    }
}
