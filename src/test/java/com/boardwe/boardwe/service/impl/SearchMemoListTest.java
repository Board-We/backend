package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.other.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.type.OpenType;
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
public class SearchMemoListTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemoRepository memoRepository;
    @InjectMocks
    MemoServiceImpl memoService;

    @Test
    @DisplayName("메모 리스트 조회 성공")
    void getMemos(){
        String boardCode= "test";
        String password="1234";
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().minusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().minusDays(1);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);

        Board board = mock(Board.class);
        Memo memo = mock(Memo.class);
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);
        MemoTheme memoTheme = mock(MemoTheme.class);

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);
       // when(board.getPassword()).thenReturn(password);
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);
        when(board.getOpenEndTime()).thenReturn(openEndTime);
        when(board.getId()).thenReturn(1L);
        when(memoRepository.findByBoardId(1L)).thenReturn(memos);
        when(memo.getId()).thenReturn(2L);
        when(memo.getMemoTheme()).thenReturn(memoTheme);
        when(memoTheme.getId()).thenReturn(3L);
        when(memo.getContent()).thenReturn("hello");

        List<MemoSelectResponseDto> response = memoService.getMemo(boardCode, password);

        Assertions.assertNotNull(response);


    }

    @Test
    @DisplayName("불가능 한 날짜에 메모 리스트 조회 실패")
    void getMemosWithWrongDays(){
        String boardCode= "test";
        String password="1234";
        LocalDateTime writingStartTime = LocalDateTime.now().minusDays(3);
        LocalDateTime writingEndTime = LocalDateTime.now().minusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);

        Board board = mock(Board.class);
        Memo memo = mock(Memo.class);
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);
        MemoTheme memoTheme = mock(MemoTheme.class);

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getOpenType()).thenReturn(OpenType.PUBLIC);
        // when(board.getPassword()).thenReturn(password);
        when(board.getWritingStartTime()).thenReturn(writingStartTime);
        when(board.getWritingEndTime()).thenReturn(writingEndTime);
        when(board.getOpenStartTime()).thenReturn(openStartTime);

        Assertions.assertThrows(BoardBeforeOpenException.class,()->{
            List<MemoSelectResponseDto> response = memoService.getMemo(boardCode, password);
        });
    }

    @Test
    @DisplayName("보드가 없는 경우")
    void getMemoWithWrongBoard(){
        String boardCode= "test";
        String password="1234";

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(BoardNotFoundException.class,()->{
            List<MemoSelectResponseDto> response = memoService.getMemo(boardCode, password);
        });

    }

    @Test
    @DisplayName("Private 보드를 접근한경우")
    void getMemoWithPrivateBoard(){
        String boardCode= "test";
        String password="1234";

        Board board = mock(Board.class);
        Memo memo = mock(Memo.class);
        List<Memo> memos = new ArrayList<>();
        memos.add(memo);


        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(board.getOpenType()).thenReturn(OpenType.PRIVATE);


        Assertions.assertThrows(InvalidPasswordException.class,()->{
            List<MemoSelectResponseDto> response = memoService.getMemo(boardCode, password);
        });
    }

}
