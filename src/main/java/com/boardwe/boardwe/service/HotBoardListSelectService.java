package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.dto.ListElementDtos.SimpleBoardResponseDto;

public interface HotBoardListSelectService {
    List<SimpleBoardResponseDto> getBoardList();
}
