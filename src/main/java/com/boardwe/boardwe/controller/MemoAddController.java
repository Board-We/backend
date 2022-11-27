package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.MemoAddRequestDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.MemoAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MemoAddController {

    private final MemoAddService memoAddService;

    @PostMapping("/board/{boardCode}/memo")
    public ResponseDto addMemo(@RequestBody MemoAddRequestDto memoAddRequestDto,
                               @PathVariable String boardCode){
        LocalDateTime openStartTime = memoAddService.addMemo(memoAddRequestDto, boardCode);
        return ResponseDto.ok("openStartTime",openStartTime);
    }
}
