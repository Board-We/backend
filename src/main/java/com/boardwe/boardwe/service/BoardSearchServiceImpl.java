package com.boardwe.boardwe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.ListElementDtos.SimpleBoardResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.TagRepository;

@Service
public class BoardSearchServiceImpl implements BoardSearchService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    MemoThemeRepository memoThemeRepository;

    @Override
    public List<Board> getBoardList(Pageable pageable, String query) {
        List<Board> result = boardRepository.findAllByTagValue(query, pageable).getContent();
        return result;
    }

    @Override
    public List<Tag> getTagListByBoard(Board board) {
        return tagRepository.findAllByBoard(board);
    }

    @Override
    public List<MemoTheme> getMemoThemeListByBoardTheme(BoardTheme boardTheme) {
        return memoThemeRepository.findAllByBoardTheme(boardTheme);
    }

    public Page<SimpleBoardResponseDto> getSimpleBoardResponseDtoPage(Pageable pageable, String query){
        List<SimpleBoardResponseDto> simpleBoardResponseDtoList = new ArrayList<SimpleBoardResponseDto>();
        List<Board> searchBoards = getBoardList(pageable, query);
        for(Board searchBoard:searchBoards){
            SimpleBoardResponseDto simpleBoardResponseDto = new SimpleBoardResponseDto();
            simpleBoardResponseDto.setBoardInfo(searchBoard);
            List<Tag> tags = getTagListByBoard(searchBoard);
            simpleBoardResponseDto.setBoardTagListValue(tags);
            BoardTheme boardTheme = searchBoard.getBoardTheme();
            List<MemoTheme> memoThemes = getMemoThemeListByBoardTheme(boardTheme);
            simpleBoardResponseDto.setThemeObject(boardTheme, memoThemes);
            simpleBoardResponseDtoList.add(simpleBoardResponseDto);
        }
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > simpleBoardResponseDtoList.size() ? simpleBoardResponseDtoList.size() : (start + pageable.getPageSize());
        Page<SimpleBoardResponseDto> simpleBoardResponseDtoPage = new PageImpl<>(simpleBoardResponseDtoList.subList(start, end), pageable, simpleBoardResponseDtoList.size());
        return simpleBoardResponseDtoPage;
    }
}
