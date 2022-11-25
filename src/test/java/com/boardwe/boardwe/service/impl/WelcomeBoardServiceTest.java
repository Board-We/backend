package com.boardwe.boardwe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.boardwe.boardwe.dto.WelcomeBoardResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.WelcomeBoardServiceImpl;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatusType;

@ExtendWith(MockitoExtension.class)
public class WelcomeBoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private MemoRepository memoRepository;

    @Mock
    private BoardThemeRepository boardThemeRepository;

    @InjectMocks
    private WelcomeBoardServiceImpl welcomBoardServiceImpl;

    @Test
    @DisplayName("보드 대문 데이터 조회")
    public void WelcomBoardServiceTest(){
        List<Memo> memos = new ArrayList<Memo>();
        setMockMemoList(memos, 2);

        List<Tag> tags = new ArrayList<Tag>();
        setMockTagList(tags, 2);
        
        BoardTheme boardTheme = mock(BoardTheme.class);
        when(boardTheme.getId()).thenReturn(1L);
        when(boardTheme.getFont()).thenReturn("godic");
        when(boardTheme.getBackgroundType()).thenReturn(BackgroundType.COLOR);
        when(boardTheme.getBackgroundColor()).thenReturn("#000000");

        LocalDateTime currenDateTime = LocalDateTime.now();
        Board board = mock(Board.class);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(board.getName()).thenReturn("test board");
        when(board.getDescription()).thenReturn("test를 위한 보드입니다.");
        when(board.getViews()).thenReturn(10);
        when(board.getWritingStartTime()).thenReturn(currenDateTime);
        when(board.getWritingEndTime()).thenReturn(currenDateTime.plusDays(1));
        when(board.getOpenStartTime()).thenReturn(currenDateTime.plusDays(2));
        when(board.getOpenEndTime()).thenReturn(currenDateTime.plusDays(3));

        when(boardRepository.findByCode("1234")).thenReturn(Optional.of(board));
        when(boardThemeRepository.findById(1L)).thenReturn(Optional.of(boardTheme));
        when(tagRepository.findAllByBoard(board)).thenReturn(tags);
        when(memoRepository.findAllByBoard(board)).thenReturn(memos);

        WelcomeBoardResponseDto welcomeBoardDto = welcomBoardServiceImpl.getWelcomBoardDto("1234");
        
        assertEquals(welcomeBoardDto.getBoardName(), board.getName());
        assertEquals(welcomeBoardDto.getBoradDescription(), board.getDescription());

        List<String> tagValues = welcomeBoardDto.getTags();
        for(int i = 0; i < tagValues.size(); i++){
            assertEquals(tagValues.get(i), tags.get(i).getValue());
        }
        assertEquals(welcomeBoardDto.getMemoCnt(), memos.size());
        assertEquals(welcomeBoardDto.getBoardViews(), board.getViews());
        assertEquals(welcomeBoardDto.getBoardStatus(), BoardStatusType.WRITING.toString());
        assertEquals(welcomeBoardDto.getWritingStartTime(), board.getWritingStartTime());
        assertEquals(welcomeBoardDto.getWritingEndTime(), board.getWritingEndTime());
        assertEquals(welcomeBoardDto.getOpenStartTime(), board.getOpenStartTime());
        assertEquals(welcomeBoardDto.getOpenEndTime(), board.getOpenEndTime());
    }

    private void setMockTagList(List<Tag> tags, int cnt){
        for(int i=0; i < cnt; i++){
            Tag tag = mock(Tag.class);
            when(tag.getValue()).thenReturn("test" + Integer.toString(i));
            tags.add(tag);
        }
    }

    private void setMockMemoList(List<Memo> memos, int cnt){
        for(int i = 0; i < cnt; i++){
            Memo memo = mock(Memo.class);
            memos.add(memo);
        }
    }
}
