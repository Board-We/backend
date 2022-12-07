package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;

public interface BoardCreateService {

    BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto);
}
