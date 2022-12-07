package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.MemoAddRequestDto;
import com.boardwe.boardwe.dto.res.MemoAddResponseDto;
import com.boardwe.boardwe.service.MemoAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoAddController {

    private final MemoAddService memoAddService;

    @PostMapping("/board/{boardCode}/memo")
    public ResponseEntity<MemoAddResponseDto> addMemo(@RequestBody MemoAddRequestDto memoAddRequestDto,
                                                     @PathVariable String boardCode){
        return ResponseEntity.ok(memoAddService.addMemo(memoAddRequestDto, boardCode));
    }
}
