package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        MemoDeleteRequestDto.builder().memoIds(memoIds).build();
        String boardCode = "test";

        Board board = mock(Board.class);
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        //when(memoRepository.findByIdAndBoard())


    }

    @Test
    @DisplayName("잘못된 메모 아이디를 보내서 메모 삭제 실패")
    void deleteMemoFailWithWrongMemoId(){}
    @Test
    @DisplayName("잘못된 보드 코드를 보내서 메모 삭제 실패")
    void deleteMemoFailWithWrongBoardCode(){}
}
