package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteMemoTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemoRepository memoRepository;
    @InjectMocks
    MemoServiceImpl memoService;

    @Test
    @DisplayName("메모 삭제 성공")
    void deleteMemo(){
        List<Long> memoIds = new ArrayList<>();
        memoIds.add(1L);
        memoIds.add(2L);

        MemoDeleteRequestDto request = MemoDeleteRequestDto.builder().memoIds(memoIds).build();
        String boardCode = "test";

        Board board = mock(Board.class);
        Memo memo = mock(Memo.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(memoRepository.findByIdAndBoard(1L,board)).thenReturn(Optional.of(memo));
        when(memoRepository.findByIdAndBoard(2L,board)).thenReturn(Optional.of(memo));
        doNothing().when(memoRepository).deleteById(any());

        memoService.deleteMemo(request,boardCode);

    }

    @Test
    @DisplayName("잘못된 메모 아이디를 보내서 메모 삭제 실패")
    void deleteMemoFailWithWrongMemoId(){
        List<Long> memoIds = new ArrayList<>();
        memoIds.add(1L);
        memoIds.add(2L);

        MemoDeleteRequestDto request = MemoDeleteRequestDto.builder().memoIds(memoIds).build();
        String boardCode = "test";

        Board board = mock(Board.class);
        Memo memo = mock(Memo.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        when(memoRepository.findByIdAndBoard(1L,board)).thenReturn(Optional.empty());

        Assertions.assertThrows(MemoNotFoundException.class,()->{
            memoService.deleteMemo(request,boardCode);
        });


    }
    @Test
    @DisplayName("잘못된 보드 코드를 보내서 메모 삭제 실패")
    void deleteMemoFailWithWrongBoardCode(){
        List<Long> memoIds = new ArrayList<>();
        memoIds.add(1L);
        memoIds.add(2L);

        MemoDeleteRequestDto request = MemoDeleteRequestDto.builder().memoIds(memoIds).build();
        String boardCode = "test";

        Board board = mock(Board.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.empty());


        Assertions.assertThrows(BoardNotFoundException.class,()->{
            memoService.deleteMemo(request,boardCode);
        });
    }
}
