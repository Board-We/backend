package com.boardwe.boardwe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.dto.WelcomeBoardResponseDto;
import com.boardwe.boardwe.service.WelcomeBoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WelcomeBoardController {
    private final WelcomeBoardService welcomeBoardService;

    @GetMapping("/board/{boardCode}/welcome")
    public ResponseDto getWelcomeBoard(@PathVariable String boardCode){
        WelcomeBoardResponseDto welcomeBoardResponseDto = welcomeBoardService.getWelcomBoardDto(boardCode);
        return ResponseDto.ok("board", welcomeBoardResponseDto);
    }
}
