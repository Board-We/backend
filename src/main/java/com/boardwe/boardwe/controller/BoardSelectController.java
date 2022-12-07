package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardSelectResponseDto;
import com.boardwe.boardwe.service.BoardSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSelectController {

    private final BoardSelectService boardSelectService;

    @GetMapping("/board/{boardCode}")
    public ResponseEntity<BoardSelectResponseDto> getBoard(@PathVariable String boardCode) {
        return ResponseEntity.ok(boardSelectService.getBoard(boardCode));
    }
}
