package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.service.BoardCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardCreateController {

    private final BoardCreateService boardCreateService;

    @PostMapping("/board")
    public ResponseEntity<BoardCreateResponseDto> create(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardCreateResponseDto boardCreateResponseDto = boardCreateService.createBoard(boardCreateRequestDto);
        return ResponseEntity.ok(boardCreateResponseDto);
    }


}
