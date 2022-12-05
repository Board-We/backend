package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.entity.Board;

public interface BoardCreateService {

    BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto);
}
