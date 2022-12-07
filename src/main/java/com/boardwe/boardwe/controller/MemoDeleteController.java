package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.service.MemoDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoDeleteController {
    private final MemoDeleteService memoDeleteService;

    @PostMapping("/board/{boardCode}/memo/delete")
    public ResponseEntity<Void> deleteMemo(@RequestBody MemoDeleteRequestDto memoDeleteRequestDto, @PathVariable String boardCode){
        memoDeleteService.deleteMemo(memoDeleteRequestDto,boardCode);
        return ResponseEntity.ok().build();
    }
}
