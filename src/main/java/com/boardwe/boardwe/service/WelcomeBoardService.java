package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.WelcomeBoardResponseDto;

public interface WelcomeBoardService {
    WelcomeBoardResponseDto getWelcomBoardDto(String boardCode);
}
