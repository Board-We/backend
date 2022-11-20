package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.BoardSelectResponseDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.BoardSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSelectController {

    private final BoardSelectService boardSelectService;

    @GetMapping("/board/{boardCode}")
    public ResponseDto getBoard(@PathVariable String boardCode) {
        BoardSelectResponseDto responseDto = boardSelectService.getBoard(boardCode);
        return ResponseDto.ok("board", responseDto);
    }

    @GetMapping("/board-theme")
    public ResponseDto getBoardThemes() {
        return ResponseDto.ok();
    }

    @GetMapping("/board/{boardCode}/memo-theme")
    public ResponseDto getMemoThemes(@PathVariable String boardCode) {
        return ResponseDto.ok();
    }

}
