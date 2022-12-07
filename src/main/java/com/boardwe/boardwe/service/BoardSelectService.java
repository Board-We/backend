package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.BoardSelectResponseDto;

public interface BoardSelectService {
    BoardSelectResponseDto getBoard(String boardCode);
}
