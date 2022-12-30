package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardDeleteTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemoRepository memoRepository;
    @Mock
    TagRepository tagRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    @DisplayName("보드를 삭제한다.")
    void deleteBoard(){
        String boardCode = "Test";
        String password = "1234";

        BoardDeleteRequestDto deleteBoard = BoardDeleteRequestDto.builder()
                .password(password)
                .build();
        Board board = mock(Board.class);

        when(board.getPassword()).thenReturn("1234");
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));
        doNothing().when(memoRepository).deleteByBoard(board);
        doNothing().when(tagRepository).deleteByBoard(board);
        doNothing().when(boardRepository).delete(board);

        boardService.deleteBoard(deleteBoard,boardCode);

    }

    @Test
    @DisplayName("잘못된 비밀번호로 보드를 삭제할때 에러 처리")
    void errorPasswordDeleteBoard(){
        String boardCode = "Test";
        String password = "1234";

        BoardDeleteRequestDto deleteBoard = BoardDeleteRequestDto.builder()
                .password(password)
                .build();
        Board board = mock(Board.class);

        when(board.getPassword()).thenReturn("1235");
        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.of(board));

        Assertions.assertThrows(InvalidPasswordException.class,()->{
            boardService.deleteBoard(deleteBoard,boardCode);
        });

    }

    @Test
    @DisplayName("없는 보드를 삭제할 때 에러처리")
    void noBoardExceptionTest(){
        String boardCode = "Test";
        String password = "1234";

        BoardDeleteRequestDto deleteBoard = BoardDeleteRequestDto.builder()
                .password(password)
                .build();
        Board board = mock(Board.class);

        when(boardRepository.findByCode(boardCode)).thenReturn(Optional.empty());

        Assertions.assertThrows(BoardNotFoundException.class,()->{
            boardService.deleteBoard(deleteBoard,boardCode);
        });
    }


}
