package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardCreateRequestDto;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.service.BoardCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCreateServiceImpl implements BoardCreateService {

    private final BoardRepository boardRepository;

    @Override
    public BoardCreateRequestDto createBoard() {
        return null;
    }
}
