package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.BoardCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardCreateController {

    private final BoardCreateService boardCreateService;

    @PostMapping("/board")
    public ResponseDto create(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        String boardUrl = boardCreateService.createBoard(boardCreateRequestDto);
        return ResponseDto.ok("boardLink", boardUrl);
    }


}
