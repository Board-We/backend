package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.BoardBeforeWritingException;
import com.boardwe.boardwe.exception.custom.BoardClosedException;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private MemoThemeRepository memoThemeRepository;
    @Mock
    private MemoRepository memoRepository;

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

        List<Memo> memos = setMemos(2);
        BoardTheme boardTheme = setBoardTheme(BackgroundType.COLOR);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        List<MemoTheme> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR),
                setMemoTheme(BackgroundType.IMAGE));

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(memoRepository.findByBoardId(board.getId()))
                .thenReturn(memos);
        when(memoThemeRepository.findByBoardThemeId(boardTheme.getId()))
                .thenReturn(memoThemes);

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
        assertMemoThemeResponseDtos(responseDto.getTheme().getMemoThemes());
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

        BoardTheme boardTheme = setBoardTheme(BackgroundType.IMAGE);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        List<MemoTheme> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR),
                setMemoTheme(BackgroundType.IMAGE));

        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(memoThemeRepository.findByBoardThemeId(boardTheme.getId()))
                .thenReturn(memoThemes);

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
        assertMemoThemeResponseDtos(responseDto.getTheme().getMemoThemes());
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

    private BoardTheme setBoardTheme(BackgroundType backgroundType) {
        BoardTheme theme = mock(BoardTheme.class);
        when(theme.getId()).thenReturn(100L);
        when(theme.getBackgroundType()).thenReturn(backgroundType);
        when(theme.getFont()).thenReturn(font);

        if (backgroundType == BackgroundType.COLOR) {
            when(theme.getBackgroundColor()).thenReturn(backgroundColor);
        } else {
            ImageInfo imageInfo = mock(ImageInfo.class);
            when(imageInfo.getUuid()).thenReturn(backgroundImageUuid);
            when(theme.getBackgroundImageInfo()).thenReturn(imageInfo);
        }

        return theme;
    }

    private MemoTheme setMemoTheme(BackgroundType backgroundType) {
        MemoTheme theme = mock(MemoTheme.class);
        when(theme.getId()).thenReturn(200L);
        when(theme.getBackgroundType()).thenReturn(backgroundType);
        when(theme.getTextColor()).thenReturn(textColor);

        if (backgroundType == BackgroundType.COLOR) {
            when(theme.getBackgroundColor()).thenReturn(backgroundColor);
        } else {
            ImageInfo imageInfo = mock(ImageInfo.class);
            when(imageInfo.getUuid()).thenReturn(backgroundImageUuid);
            when(theme.getBackgroundImageInfo()).thenReturn(imageInfo);
        }
        return theme;
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

    private void assertMemoThemeResponseDtos(List<MemoThemeSelectResponseDto> memoThemesWithId) {
        assertEquals(BackgroundType.COLOR, memoThemesWithId.get(0).getMemoBackgroundType());
        assertEquals(backgroundColor, memoThemesWithId.get(0).getMemoBackground());
        assertEquals(textColor, memoThemesWithId.get(0).getMemoTextColor());
        assertEquals(BackgroundType.IMAGE, memoThemesWithId.get(1).getMemoBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, memoThemesWithId.get(1).getMemoBackground());
        assertEquals(textColor, memoThemesWithId.get(1).getMemoTextColor());
    }
}