package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.service.BoardSelectService;
import com.boardwe.boardwe.type.BoardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardSelectServiceImpl implements BoardSelectService {

    private final BoardRepository boardRepository;

    @Override
    public BoardSelectResponseDto getBoard(String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        return BoardSelectResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .openType(board.getOpenType())
                .boardStatus(BoardStatus.OPEN)
                .build();
    }
}
