package com.boardwe.boardwe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.ThemeUtil;

@ExtendWith(MockitoExtension.class)
public class BoardSearchServiceImplTest {
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ThemeUtil themeUtil;
    @Mock
    private BoardInfoUtil boardInfoUtil;
    @InjectMocks
    private BoardSearchServiceImpl boardSearchServiceImpl;

    @Test
    @DisplayName("보드의 태그를 통한 검색")
    void searchBoards(){
        List<Board> boards = setBoard();
        List<Tag> tags = setTag();
        String query = "크리스마스";
        int size = 10;
        int page = 0;
        Pageable pageable = PageRequest.of(page, size);

        when(boardRepository.findAllByTagValue(query, pageable)).thenReturn(boards);
        for(int i = 0; i < 10; i++){
            when(boardInfoUtil.getBoardLink(Integer.toString(i))).thenReturn("/board/" + Integer.toString(i));
            when(tagRepository.findAllByBoardId((long) i)).thenReturn(tags);
        }

        Page<BoardSearchResponseDto> searchResultBoards = boardSearchServiceImpl.searchBoardByTagWithPaging(query, pageable);
        List<String> tagValues = new ArrayList<>();

        tagValues.add("크리스마스");
        tagValues.add("test");

        for(int i = 0; i < 10; i++){
            assertEquals(boards.get(i).getName(), searchResultBoards.getContent().get(i).getBoardName());
            assertEquals("/board/" + boards.get(i).getCode(), searchResultBoards.getContent().get(i).getBoardLink());
            assertEquals(boards.get(i).getViews(), searchResultBoards.getContent().get(i).getBoardViews());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundType(), searchResultBoards.getContent().get(i).getBoardBackgroundType());
            assertEquals(boards.get(i).getBoardTheme().getFont(), searchResultBoards.getContent().get(i).getBoardFont());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundColor(), searchResultBoards.getContent().get(i).getBoardBackground());
            assertEquals(tagValues, searchResultBoards.getContent().get(i).getBoardTags());
            assertEquals(10, searchResultBoards.getSize());
        }
    }

    @Test
    @DisplayName("조회수가 높은 보드 10개 조회")
    void getHotBoards(){
        List<Board> boards = setBoard();
        List<Tag> tags = setTag();

        when(boardRepository.findTop10ByOpenTypeOrderByViewsDesc(OpenType.PUBLIC)).thenReturn(boards);
        for(int i = 0; i < 10; i++){
            when(boardInfoUtil.getBoardLink(Integer.toString(i))).thenReturn("/board/" + Integer.toString(i));
            when(tagRepository.findAllByBoardId((long) i)).thenReturn(tags);
        }

        List<BoardSearchResponseDto> hotBoards = boardSearchServiceImpl.selectHotBoards();
        List<String> tagValues = new ArrayList<>();

        tagValues.add("크리스마스");
        tagValues.add("test");

        for(int i = 0; i < 10; i++){
            assertEquals(boards.get(i).getName(), hotBoards.get(i).getBoardName());
            assertEquals("/board/" + boards.get(i).getCode(), hotBoards.get(i).getBoardLink());
            assertEquals(boards.get(i).getViews(), hotBoards.get(i).getBoardViews());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundType(), hotBoards.get(i).getBoardBackgroundType());
            assertEquals(boards.get(i).getBoardTheme().getFont(), hotBoards.get(i).getBoardFont());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundColor(), hotBoards.get(i).getBoardBackground());
            assertEquals(tagValues, hotBoards.get(i).getBoardTags());
        }
    }

    @Test
    @DisplayName("랜덤한 10개의 보드 추천")
    void getRecommendBoards(){
        List<Board> boards = setBoard();
        List<Tag> tags = setTag();

        when(boardRepository.find10OpenBoardsOderByRandom()).thenReturn(boards);
        for(int i = 0; i < 10; i++){
            when(boardInfoUtil.getBoardLink(Integer.toString(i))).thenReturn("/board/" + Integer.toString(i));
            when(tagRepository.findAllByBoardId((long) i)).thenReturn(tags);
        }

        List<BoardSearchResponseDto> recommendBoards = boardSearchServiceImpl.selectRecommendBoards();
        List<String> tagValues = new ArrayList<>();

        tagValues.add("크리스마스");
        tagValues.add("test");

        for(int i = 0; i < 10; i++){
            assertEquals(boards.get(i).getName(), recommendBoards.get(i).getBoardName());
            assertEquals("/board/" + boards.get(i).getCode(), recommendBoards.get(i).getBoardLink());
            assertEquals(boards.get(i).getViews(), recommendBoards.get(i).getBoardViews());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundType(), recommendBoards.get(i).getBoardBackgroundType());
            assertEquals(boards.get(i).getBoardTheme().getFont(), recommendBoards.get(i).getBoardFont());
            assertEquals(boards.get(i).getBoardTheme().getBackgroundColor(), recommendBoards.get(i).getBoardBackground());
            assertEquals(tagValues, recommendBoards.get(i).getBoardTags());
        }
    }

    private List<Board> setBoard(){
        List<Board> boards = new ArrayList<>();
        BoardTheme boardTheme = setBoardTheme();
        for(int i = 0; i < 10; i++){
            Board board = mock(Board.class);
            when(board.getId()).thenReturn((long) i);
            when(board.getName()).thenReturn("test" + Integer.toString(i));
            when(board.getCode()).thenReturn(Integer.toString(i));
            when(board.getViews()).thenReturn(i);
            when(board.getBoardTheme()).thenReturn(boardTheme);
            boards.add(board);
        }
        return boards;
    }

    private BoardTheme setBoardTheme(){
        BoardTheme boardTheme = mock(BoardTheme.class);
        when(boardTheme.getFont()).thenReturn("Arial");
        when(boardTheme.getBackgroundType()).thenReturn(BackgroundType.COLOR);
        when(boardTheme.getBackgroundColor()).thenReturn("#FFFFFF");
        when(themeUtil.getBackgroundValue(boardTheme)).thenReturn("#FFFFFF");
        return boardTheme;
    }

    private List<Tag> setTag(){
        List<Tag> tags = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            Tag tag = mock(Tag.class);
            if(i % 2 == 0){
                when(tag.getValue()).thenReturn("크리스마스");
            }
            else{
                when(tag.getValue()).thenReturn("test");
            }
            tags.add(tag);
        }
        return tags;
    }
}
