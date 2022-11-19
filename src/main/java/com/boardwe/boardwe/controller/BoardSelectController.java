package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardSelectController {

    @GetMapping("/board/{boardCode}")
    public ResponseDto getBoard(@PathVariable String boardCode) {
        return ResponseDto.ok();
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
