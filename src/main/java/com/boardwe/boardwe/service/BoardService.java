package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.res.BoardReadResponseDto;

public interface BoardService {
    BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto);
    BoardReadResponseDto readBoard(String boardCode);
    void deleteBoard(String boardCode);
}
