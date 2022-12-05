package com.boardwe.boardwe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.HotBoardListSelectService;
import com.boardwe.boardwe.service.RecommendBoardListSelectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardListSelectController {
    private final HotBoardListSelectService hotBoardListSelectService;
    private final RecommendBoardListSelectService recommendBoardListSelectService;

    @GetMapping("/board/hot")
    public ResponseDto getHotBoards(){
        return ResponseDto.ok("boards", hotBoardListSelectService.getBoardList());
    }

    @GetMapping("/board/recommend")
    public ResponseDto getRecommendBoards(){
        return ResponseDto.ok("boards", recommendBoardListSelectService.getBoardList());
    }
}
