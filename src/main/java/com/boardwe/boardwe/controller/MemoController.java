package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.res.MemoSelectResponseDto;
import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;
import com.boardwe.boardwe.service.MemoService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public ResponseEntity<Void> deleteMemo(@RequestBody MemoDeleteRequestDto memoDeleteRequestDto, @PathVariable String boardCode, HttpSession session){
        if(session.getAttribute("boardCode") == null || !boardCode.equals(session.getAttribute("boardCode").toString())){
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        memoService.deleteMemo(memoDeleteRequestDto,boardCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/board/{boardCode}/memo/search")
    public ResponseEntity<MemoSearchResponseDto> searchMemo(@PathVariable String boardCode,
                                                            @RequestParam String query){
        return ResponseEntity.ok(memoService.searchMemo(boardCode,query));
    }

    @GetMapping("/board/{boardCode}/memos")
    public ResponseEntity<List<MemoSelectResponseDto>> getMemos(@PathVariable String boardCode, @RequestParam(required = false) String password){
        return ResponseEntity.ok(memoService.getMemo(boardCode,password));
    }
}
