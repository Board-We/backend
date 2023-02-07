package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.req.BoardLoginRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.exception.custom.other.InvalidAccessException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.service.BoardSearchService;
import com.boardwe.boardwe.service.BoardService;
import com.boardwe.boardwe.service.LoginService;
import com.boardwe.boardwe.type.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.boardwe.boardwe.type.SessionConst.LOGIN_SESSION_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final LoginService loginService;
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
    public ResponseEntity<Void> delete(
            @PathVariable String boardCode,
            @SessionAttribute(name = LOGIN_SESSION_ID, required = false) String sessionValue
            ){
        if (sessionValue == null || !sessionValue.equals(boardCode)){
            log.info("[BoardController] Invalid Session: {}", sessionValue);
            throw new InvalidAccessException();
        }
        boardService.deleteBoard(boardCode);
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
        String boardCode = boardLoginRequestDto.getBoardCode();
        Boolean canLogin = loginService.login(boardCode, boardLoginRequestDto.getPassword());
        if (canLogin){
            session.setAttribute(SessionConst.LOGIN_SESSION_ID, boardCode);
            log.info("[BoardController] Login Succeed to board {}", boardCode);
        } else {
            throw new InvalidPasswordException();
        }
        return ResponseEntity.ok().build();
    }
}
