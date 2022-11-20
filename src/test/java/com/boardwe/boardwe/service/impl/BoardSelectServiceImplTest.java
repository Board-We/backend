package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.exception.custom.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.BoardBeforeWritingException;
import com.boardwe.boardwe.exception.custom.BoardClosedException;
import com.boardwe.boardwe.repository.BoardRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = writingStartTime.plusDays(1);
        LocalDateTime openStartTime = LocalDateTime.now().minusDays(1);
        LocalDateTime openEndTime = openStartTime.plusDays(2);

        when(board.getName()).thenReturn(boardName);
        when(board.getDescription()).thenReturn(boardDesc);
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
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

    @Test
    @DisplayName("작성 상태의 PUBLIC 보드를 조회한다.")
    void get_writing_board_success() throws Exception {
        // given
        String boardCode = "1234";
        Board board = mock(Board.class);
        String boardName = "내 생일을 축하해줘";
        String boardDesc = "생일 축하하는 메시지를 써줘";
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(1);
        LocalDateTime writingEndTime = writingStartTime.plusDays(2);
        LocalDateTime openStartTime = writingEndTime.plusDays(3);
        LocalDateTime openEndTime = openStartTime.plusDays(1);

        when(board.getName()).thenReturn(boardName);
        when(board.getDescription()).thenReturn(boardDesc);
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
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
        assertEquals(BoardStatus.WRITING, responseDto.getBoardStatus());
    }

    @Test
    @DisplayName("작성 이전 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_before_writing_board_success() throws Exception {
        // given
        String boardCode = "1234";
        Board board = mock(Board.class);
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = writingStartTime.plusDays(2);
        LocalDateTime openStartTime = writingEndTime.plusDays(1);
        LocalDateTime openEndTime = openStartTime.plusDays(1);

        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
//        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));

        // when & then
        assertThrows(BoardBeforeWritingException.class, () -> boardSelectService.getBoard(boardCode));
    }

    @Test
    @DisplayName("공개 이전 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_before_open_board_success() throws Exception {
        // given
        String boardCode = "1234";
        Board board = mock(Board.class);
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(5);
        LocalDateTime writingEndTime = writingStartTime.plusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime openEndTime = openStartTime.plusDays(1);

        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
//        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));

        // when & then
        assertThrows(BoardBeforeOpenException.class, () -> boardSelectService.getBoard(boardCode));
    }

    @Test
    @DisplayName("공개 종료 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_closed_board_success() throws Exception {
        // given
        String boardCode = "1234";
        Board board = mock(Board.class);
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(10);
        LocalDateTime writingEndTime = writingStartTime.plusDays(2);
        LocalDateTime openStartTime = writingEndTime.plusDays(1);
        LocalDateTime openEndTime = openStartTime.plusDays(1);

        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
//        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));

        // when & then
        assertThrows(BoardClosedException.class, () -> boardSelectService.getBoard(boardCode));
    }


}