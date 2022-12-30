package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.BoardCannotWriteException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.util.ThemeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateMemoTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemoThemeRepository memoThemeRepository;
    @Mock
    MemoRepository memoRepository;
    @Mock
    ThemeUtil themeUtil;
    @InjectMocks
    MemoServiceImpl memoService;
    @Test
    @DisplayName("메모 붙이기 성공")
    void addMemo(){
        String boardCode ="test";
        String memoContent ="hello";
        Long memoThemeId= 4L;

        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(1);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openEndTime = openStartTime.plusDays(2);

        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        MemoTheme memoTheme = mock(MemoTheme.class);
        Memo memo = mock(Memo.class);
        BoardThemeSelectResponseDto boardThemeDto = mock(BoardThemeSelectResponseDto.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(memoThemeRepository.findByIdAndBoardTheme(memoThemeId,boardTheme)).thenReturn(Optional.of(memoTheme));
        when(memoRepository.save(any())).thenReturn(memo);

        MemoCreateRequestDto memoRequest = MemoCreateRequestDto.builder().memoContent(memoContent).memoThemeId(memoThemeId).build();
        MemoCreateResponseDto result = memoService.createMemo(memoRequest, boardCode);

        Assertions.assertEquals(result.getOpenStartTime(), openStartTime);


    }

    @Test
    @DisplayName("잘못된 보드코드를 전달받아 메모 붙이기 실패")
    void addMemoFailWithWrongBoardCode(){
        String boardCode ="testFail";
        String memoContent ="hello";
        Long memoThemeId= 4L;

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.empty());

        MemoCreateRequestDto memoRequest = MemoCreateRequestDto.builder().memoContent(memoContent).memoThemeId(memoThemeId).build();

        Assertions.assertThrows(BoardNotFoundException.class,()->{
            MemoCreateResponseDto result = memoService.createMemo(memoRequest, boardCode);
        });

    }

    @Test
    @DisplayName("보드에 없는 메모 테마로 메모를 보냈을 때 에러처리")
    void addMemoFailWithWrongMemoTheme(){
        String boardCode ="testFail";
        String memoContent ="hello";
        Long memoThemeId= 0L;

        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(1);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openEndTime = openStartTime.plusDays(2);

        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        MemoTheme memoTheme = mock(MemoTheme.class);
        Memo memo = mock(Memo.class);
        BoardThemeSelectResponseDto boardThemeDto = mock(BoardThemeSelectResponseDto.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(memoThemeRepository.findByIdAndBoardTheme(memoThemeId,boardTheme)).thenReturn(Optional.empty());

        MemoCreateRequestDto memoRequest = MemoCreateRequestDto.builder().memoContent(memoContent).memoThemeId(memoThemeId).build();

        Assertions.assertThrows(MemoThemeNotFoundException.class,()->{
            MemoCreateResponseDto result = memoService.createMemo(memoRequest, boardCode);
        });
    }

    @Test
    @DisplayName("WRITING이 아닐때 메모를 붙이려고 시도시 에러처리")
    void addMemoFailWithWrongTime(){
        String boardCode ="test";
        String memoContent ="hello";
        Long memoThemeId= 4L;

        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().minusDays(1);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openEndTime = openStartTime.plusDays(2);

        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        MemoTheme memoTheme = mock(MemoTheme.class);
        Memo memo = mock(Memo.class);
        BoardThemeSelectResponseDto boardThemeDto = mock(BoardThemeSelectResponseDto.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);

        MemoCreateRequestDto memoRequest = MemoCreateRequestDto.builder().memoContent(memoContent).memoThemeId(memoThemeId).build();
        Assertions.assertThrows(BoardCannotWriteException.class,()->{
            MemoCreateResponseDto result = memoService.createMemo(memoRequest, boardCode);
        });
    }

}
