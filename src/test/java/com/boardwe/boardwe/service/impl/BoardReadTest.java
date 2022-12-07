package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.BoardBeforeWritingException;
import com.boardwe.boardwe.exception.custom.BoardClosedException;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.ThemeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardReadTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private MemoRepository memoRepository;
    @Mock
    private ThemeUtil themeUtil;

    @InjectMocks
    private BoardServiceImpl boardService;

    private final String backgroundColor = "#FFFFFF",
            backgroundImageUuid = "abcd",
            font = "Batang",
            textColor = "#000000";


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

        when(board.getId()).thenReturn(1L);
        when(board.getName()).thenReturn(boardName);
        when(board.getDescription()).thenReturn(boardDesc);
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);

        BoardTheme boardTheme = Mockito.mock(BoardTheme.class);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        BoardThemeSelectResponseDto boardThemeDto = BoardThemeSelectResponseDto.builder()
                .boardBackgroundType(BackgroundType.COLOR)
                .boardBackground(backgroundColor)
                .boardFont(font)
                .build();

        List<Memo> memos = setMemos(2);
        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(memoRepository.findByBoardId(board.getId()))
                .thenReturn(memos);
        when(themeUtil.getBoardThemeSelectResponseDto(boardTheme))
                .thenReturn(boardThemeDto);

        // when
        BoardReadResponseDto responseDto = boardService.readBoard(boardCode);

        // then
        assertEquals(boardName, responseDto.getBoardName());
        assertEquals(boardDesc, responseDto.getBoardDescription());
        assertEquals(openStartTime, responseDto.getOpenStartTime());
        assertEquals(openEndTime, responseDto.getOpenEndTime());
        assertEquals(OpenType.PUBLIC, responseDto.getOpenType());
        assertEquals(BoardStatus.OPEN, responseDto.getBoardStatus());

        assertEquals(2, responseDto.getMemos().size());
        assertEquals(201L, responseDto.getMemos().get(0).getMemoThemeId());
        assertEquals("memo 1", responseDto.getMemos().get(0).getMemoContent());
        assertEquals(202L, responseDto.getMemos().get(1).getMemoThemeId());
        assertEquals("memo 2", responseDto.getMemos().get(1).getMemoContent());

        assertEquals(BackgroundType.COLOR, responseDto.getTheme().getBoardBackgroundType());
        assertEquals(backgroundColor, responseDto.getTheme().getBoardBackground());
        assertEquals(font, responseDto.getTheme().getBoardFont());
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

        BoardTheme boardTheme = Mockito.mock(BoardTheme.class);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        BoardThemeSelectResponseDto boardThemeDto = BoardThemeSelectResponseDto.builder()
                .boardBackgroundType(BackgroundType.IMAGE)
                .boardBackground("/image/" + backgroundImageUuid)
                .boardFont(font)
                .build();

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(themeUtil.getBoardThemeSelectResponseDto(boardTheme))
                .thenReturn(boardThemeDto);


        // when
        BoardReadResponseDto responseDto = boardService.readBoard(boardCode);

        // then
        assertEquals(boardName, responseDto.getBoardName());
        assertEquals(boardDesc, responseDto.getBoardDescription());
        assertEquals(openStartTime, responseDto.getOpenStartTime());
        assertEquals(openEndTime, responseDto.getOpenEndTime());
        assertEquals(OpenType.PUBLIC, responseDto.getOpenType());
        assertNull(responseDto.getMemos());

        assertEquals(BoardStatus.WRITING, responseDto.getBoardStatus());
        assertEquals(BackgroundType.IMAGE, responseDto.getTheme().getBoardBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, responseDto.getTheme().getBoardBackground());
        assertEquals(font, responseDto.getTheme().getBoardFont());
    }

    @Test
    @DisplayName("작성 이전 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_before_writing_board() throws Exception {
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
        assertThrows(BoardBeforeWritingException.class, () -> boardService.readBoard(boardCode));
    }

    @Test
    @DisplayName("공개 이전 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_before_open_board() throws Exception {
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
        assertThrows(BoardBeforeOpenException.class, () -> boardService.readBoard(boardCode));
    }

    @Test
    @DisplayName("공개 종료 상태의 PUBLIC 보드를 조회하는데 실패한다.")
    void get_closed_board() throws Exception {
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
        assertThrows(BoardClosedException.class, () -> boardService.readBoard(boardCode));
    }

    @Test
    @DisplayName("존재하지 않는 보드를 조회하는데 실패한다.")
    void get_not_exist_board() throws Exception {
        // given
        String boardCode = "1234";
        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(BoardNotFoundException.class, () -> boardService.readBoard(boardCode));
    }

    private List<Memo> setMemos(int num) {
        List<Memo> memos = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            Memo memo = mock(Memo.class);
            MemoTheme memoTheme = mock(MemoTheme.class);
            when(memoTheme.getId()).thenReturn(200L + i);
            when(memo.getMemoTheme()).thenReturn(memoTheme);
            when(memo.getContent()).thenReturn(String.format("memo %d", i));
            memos.add(memo);
        }
        return memos;
    }
}