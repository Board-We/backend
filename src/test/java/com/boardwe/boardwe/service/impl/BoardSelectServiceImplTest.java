package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.service.BoardSelectService;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardSelectServiceImplTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardSelectServiceImpl boardSelectService;

    @Test
    @DisplayName("공개 상태의 PUBLIC 보드를 조회한다.")
    void get_open_board_success() throws Exception {
        // given
        String boardCode = "1234";
        Board board = mock(Board.class);
        String boardName = "내 생일을 축하해줘";
        String boardDesc = "생일 축하하는 메시지를 써줘";
        LocalDateTime openStartTime = LocalDateTime.of(2022, 9, 15, 0, 0);
        LocalDateTime openEndTime = LocalDateTime.of(2022, 9, 18, 23, 59, 59);

        when(board.getName()).thenReturn(boardName);
        when(board.getDescription()).thenReturn(boardDesc);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));

        // when
        BoardSelectResponseDto responseDto = boardSelectService.getBoard(boardCode);
        
        // then
        assertEquals(boardName, responseDto.getBoardName());
        assertEquals(boardDesc, responseDto.getBoardDescription());
        assertEquals(openStartTime, responseDto.getOpenStartTime());
        assertEquals(openEndTime, responseDto.getOpenEndTime());
        assertEquals(OpenType.PUBLIC, responseDto.getOpenType());
        assertEquals(BoardStatus.OPEN, responseDto.getBoardStatus());
    }


}