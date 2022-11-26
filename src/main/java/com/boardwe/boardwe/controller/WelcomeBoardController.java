package com.boardwe.boardwe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.service.HotBoardListServiceImpl;
import com.boardwe.boardwe.service.RecommandBoardListServiceImpl;
import com.boardwe.boardwe.service.WelcomeBoardServiceImpl;

@RestController
public class WelcomeBoardController {

    @Autowired
    WelcomeBoardServiceImpl welcomBoardServiceImpl;

    @Autowired
    HotBoardListServiceImpl hotBoardListServiceImpl;

    @Autowired
    RecommandBoardListServiceImpl recommandBoardListServiceImpl;

    @GetMapping("/board/{boardCode}/welcome")
    @ResponseBody
    public ResponseDto getWelcomeBoard(@PathVariable String boardCode){
        return ResponseDto.ok("board", welcomBoardServiceImpl.getWelcomBoardDto(boardCode));
    }

    @GetMapping("/board/search")
    public ResponseDto searchBoardWithTag(@RequestParam String query, @RequestParam int page, @RequestParam int size){
        return null;
    }

    @GetMapping("/board/hot")
    public ResponseDto getHotBoard(@PageableDefault(page = 0, size = 10, sort = "views", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseDto.ok("boards", hotBoardListServiceImpl.getSimpleBoardResponseDtoList(pageable));
    }

    @GetMapping("/board/recommand")
    public ResponseDto getRecommandBoard(){
        return ResponseDto.ok("boards", recommandBoardListServiceImpl.getSimpleBoardResponseDtoList());
    }
}
