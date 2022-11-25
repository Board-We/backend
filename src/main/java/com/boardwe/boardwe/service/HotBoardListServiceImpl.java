package com.boardwe.boardwe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.ListElementDtos.SimpleBoardResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.type.OpenType;

@Service
public class HotBoardListServiceImpl implements BoardListService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    BoardThemeRepository boardThemeRepository;

    @Autowired
    MemoThemeRepository memoThemeRepository;
    
    @Override
    public List<Board> getHotBoardList(Pageable pageable) {
        return boardRepository.findAllByOpenType(OpenType.PUBLIC ,pageable);
    }

    @Override
    public List<Tag> getTagListByBoard(Board board) {
        return tagRepository.findAllByBoard(board);
    }

    @Override
    public List<MemoTheme> getMemoThemeListByBoardTheme(BoardTheme boardTheme) {
        return memoThemeRepository.findAllByBoardTheme(boardTheme);
    }

    public List<SimpleBoardResponseDto> getSimpleBoardResponseDtoList(Pageable pageable){
        List<SimpleBoardResponseDto> simpleBoardResponseDtos = new ArrayList<SimpleBoardResponseDto>();
        List<Board> hotBoards = getHotBoardList(pageable);
        for(Board hotBoard:hotBoards){
            SimpleBoardResponseDto simpleBoardResponseDto = new SimpleBoardResponseDto();
            simpleBoardResponseDto.setBoardInfo(hotBoard);
            List<Tag> tags = getTagListByBoard(hotBoard);
            simpleBoardResponseDto.setBoardTagListValue(tags);
            BoardTheme boardTheme = hotBoard.getBoardTheme();
            List<MemoTheme> memoThemes = getMemoThemeListByBoardTheme(boardTheme);
            simpleBoardResponseDto.setThemeObject(boardTheme, memoThemes);
            simpleBoardResponseDtos.add(simpleBoardResponseDto);
        }
        return simpleBoardResponseDtos;
    }
}
