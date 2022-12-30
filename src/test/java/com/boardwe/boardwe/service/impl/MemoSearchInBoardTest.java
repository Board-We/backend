package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.util.ThemeUtil;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemoSearchInBoardTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemoRepository memoRepository;
    @Mock
    ThemeUtil themeUtil;
    @InjectMocks
    MemoServiceImpl memoService;

    @Test
    @DisplayName("보드 내 메모 검색 성공")
    public void memoSearch(){
        String boardCode = "Test";
        String query = "keyword";
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().minusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().minusDays(1);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);

        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        BoardThemeSelectResponseDto boardThemeDto = mock(BoardThemeSelectResponseDto.class);
        Memo memo = mock(Memo.class);
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);
        MemoTheme memoTheme = mock(MemoTheme.class);

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(themeUtil.getBoardThemeSelectResponseDto(boardTheme)).thenReturn(boardThemeDto);
        when(memoRepository.findByBoardAndContentContains(board,query)).thenReturn(memos);
        when(memo.getMemoTheme()).thenReturn(memoTheme);
        when(memo.getContent()).thenReturn("keyword");
        MemoSearchResponseDto memoSearchResponseDto = memoService.searchMemo(boardCode, query);

        Assertions.assertNotNull(memoSearchResponseDto);


    }

    @Test
    @DisplayName("없는 보드에서 메모 검색 실패")
    public void memoSearchFailWithWrongBoard(){
        String boardCode = "Test";
        String query = "keyword";

        Memo memo = mock(Memo.class);
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(BoardNotFoundException.class,()->{
            MemoSearchResponseDto memoSearchResponseDto = memoService.searchMemo(boardCode, query);
        });
    }

    @Test
    @DisplayName("보드 내 없는 메모")
    public void memoSearchWithNoResul(){
        String boardCode = "Test";
        String query = "keyword";
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().minusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().minusDays(1);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);

        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        BoardThemeSelectResponseDto boardThemeDto = mock(BoardThemeSelectResponseDto.class);
        List<Memo> memos = new ArrayList<>();

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(themeUtil.getBoardThemeSelectResponseDto(boardTheme)).thenReturn(boardThemeDto);
        when(memoRepository.findByBoardAndContentContains(board,query)).thenReturn(memos);

        MemoSearchResponseDto memoSearchResponseDto = memoService.searchMemo(boardCode, query);

        Assertions.assertNotNull(memoSearchResponseDto);

    }


}
