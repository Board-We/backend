package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;
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
    private TagRepository tagRepository;
    @Mock
    private ThemeUtil themeUtil;

    @InjectMocks
    private BoardServiceImpl boardService;

    private final String backgroundColor = "#FFFFFF",
            backgroundImageUuid = "abcd",
            font = "Batang",
            textColor = "#000000";


    @Test
    @DisplayName("PUBLIC 보드를 조회한다.")
    void get_public_board_success() throws Exception {
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

        BoardTheme boardTheme = BoardTheme.builder()
                .backgroundType(BackgroundType.COLOR)
                .backgroundColor(backgroundColor)
                .font(font)
                .build();
        when(board.getBoardTheme()).thenReturn(boardTheme);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(themeUtil.getBackgroundValue(boardTheme))
                .thenReturn(backgroundColor);

        // when
        BoardReadResponseDto responseDto = boardService.readBoard(boardCode);

        // then
        assertEquals(boardName, responseDto.getBoardName());
        assertEquals(boardDesc, responseDto.getBoardDescription());
        assertEquals(openStartTime, responseDto.getOpenStartTime());
        assertEquals(openEndTime, responseDto.getOpenEndTime());
        assertEquals(OpenType.PUBLIC, responseDto.getOpenType());
        assertEquals(BoardStatus.OPEN, responseDto.getBoardStatus());

        assertEquals(BackgroundType.COLOR, responseDto.getBoardBackgroundType());
        assertEquals(backgroundColor, responseDto.getBoardBackground());
        assertEquals(font, responseDto.getBoardFont());
    }
    @Test
    @DisplayName("PRIVATE 보드를 조회한다.")
    void get_private_board_success() throws Exception {
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
        when(board.getOpenType()).thenReturn(OpenType.PRIVATE);

        BoardTheme boardTheme = BoardTheme.builder()
                .font(font)
                .build();
        when(board.getBoardTheme()).thenReturn(boardTheme);

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));

        // when
        BoardReadResponseDto responseDto = boardService.readBoard(boardCode);

        // then
        assertEquals(boardName, responseDto.getBoardName());
        assertEquals(boardDesc, responseDto.getBoardDescription());
        assertEquals(openStartTime, responseDto.getOpenStartTime());
        assertEquals(openEndTime, responseDto.getOpenEndTime());
        assertEquals(OpenType.PRIVATE, responseDto.getOpenType());
        assertEquals(BoardStatus.OPEN, responseDto.getBoardStatus());
        assertEquals(font, responseDto.getBoardFont());
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