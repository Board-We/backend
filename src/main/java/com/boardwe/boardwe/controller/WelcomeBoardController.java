package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.WelcomeBoardResponseDto;
import com.boardwe.boardwe.service.WelcomeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WelcomeBoardController {
    private final WelcomeBoardService welcomeBoardService;

    @GetMapping("/board/{boardCode}/welcome")
    public ResponseEntity<WelcomeBoardResponseDto> getWelcomeBoard(@PathVariable String boardCode){
        return ResponseEntity.ok(welcomeBoardService.getWelcomBoardDto(boardCode));
    }
}
