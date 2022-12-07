package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.service.BoardDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardDeleteController {
    private final BoardDeleteService boardDeleteService;

    @PostMapping("/board/{boardCode}/delete")
    public ResponseEntity<Void> delete(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto, @PathVariable String boardCode){
        boardDeleteService.deleteBoard(boardDeleteRequestDto,boardCode);
        return ResponseEntity.ok().build();
    }

}
