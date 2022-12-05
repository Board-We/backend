package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.MemoDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemoDeleteController {
    private final MemoDeleteService memoDeleteService;

    @PostMapping("/board/{boardCode}/memo/delete")
    public ResponseDto deleteMemo(@RequestBody MemoDeleteRequestDto memoDeleteRequestDto, @PathVariable String boardCode){
        memoDeleteService.deleteMemo(memoDeleteRequestDto,boardCode);
        return ResponseDto.ok();
    }
}
