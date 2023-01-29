package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.req.BoardLoginRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.service.BoardSearchService;
import com.boardwe.boardwe.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardSearchService boardSearchService;

    @PostMapping("/board")
    public ResponseEntity<BoardCreateResponseDto> create(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        BoardCreateResponseDto boardCreateResponseDto = boardService.createBoard(boardCreateRequestDto);
        return ResponseEntity.ok(boardCreateResponseDto);
    }

    @GetMapping("/board/{boardCode}")
    public ResponseEntity<BoardReadResponseDto> read(@PathVariable String boardCode) {
        return ResponseEntity.ok(boardService.readBoard(boardCode));
    }

    @PostMapping("/board/{boardCode}/delete")
    public ResponseEntity<Void> delete(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto, @PathVariable String boardCode){
        boardService.deleteBoard(boardDeleteRequestDto,boardCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/board/search")
    public ResponseEntity<Page<BoardSearchResponseDto>> searchBoardsByTagValue(@RequestParam String query, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(boardSearchService.searchBoardByTagWithPaging(query, pageable));
    }

    @GetMapping("/boards/hot")
    public ResponseEntity<List<BoardSearchResponseDto>> getHotBoards(){
        return ResponseEntity.ok(boardSearchService.selectHotBoards());
    }

    @GetMapping("/boards/recommend")
    public ResponseEntity<List<BoardSearchResponseDto>> getRecommendBoards(){
        return ResponseEntity.ok(boardSearchService.selectRecommendBoards());
    }

    @PostMapping("/board/login")
    public ResponseEntity<Void> login(@RequestBody BoardLoginRequestDto boardLoginRequestDto, HttpSession session){
        return null;
    }
}
