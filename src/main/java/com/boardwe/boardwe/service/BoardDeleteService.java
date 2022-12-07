package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;

public interface BoardDeleteService {
    void deleteBoard(BoardDeleteRequestDto requestDto, String boardCode);
}
