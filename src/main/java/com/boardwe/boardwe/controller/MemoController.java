package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/board/{boardCode}/memo")
    public ResponseEntity<MemoCreateResponseDto> addMemo(@RequestBody MemoCreateRequestDto memoCreateRequestDto,
                                                         @PathVariable String boardCode){
        return ResponseEntity.ok(memoService.createMemo(memoCreateRequestDto, boardCode));
    }

    @PostMapping("/board/{boardCode}/memo/delete")
    public ResponseEntity<Void> deleteMemo(@RequestBody MemoDeleteRequestDto memoDeleteRequestDto, @PathVariable String boardCode){
        memoService.deleteMemo(memoDeleteRequestDto,boardCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/board/{boardCode}/memo/search")
    public ResponseEntity<MemoSearchResponseDto> searchMemo(@PathVariable String boardCode,
                                                            @RequestParam String query){
        return ResponseEntity.ok(memoService.searchMemo(boardCode,query));
    }
}
