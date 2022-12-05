package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.BoardDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardDeleteController {
    private final BoardDeleteService boardDeleteService;

    @PostMapping("/board/{boardCode}/delete")
    public ResponseDto delete(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto, @PathVariable String boardCode){
        boardDeleteService.deleteBoard(boardDeleteRequestDto,boardCode);
        return ResponseDto.ok();
    }

}
