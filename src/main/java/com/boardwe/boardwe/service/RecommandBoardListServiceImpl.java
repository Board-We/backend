package com.boardwe.boardwe.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecommandBoardListServiceImpl implements RecommandBoardListService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    MemoThemeRepository memoThemeRepository;
    
    @Override
    public List<Board> getBoardList() {
        return boardRepository.find10RandomOpenBoards();
    }

    @Override
    public List<Tag> getTagListByBoard(Board board) {
        return tagRepository.findAllByBoard(board);
    }

    @Override
    public List<MemoTheme> getMemoThemeListByBoardTheme(BoardTheme boardTheme) {
        return memoThemeRepository.findAllByBoardTheme(boardTheme);
    }

    public List<SimpleBoardResponseDto> getSimpleBoardResponseDtoList(){
        List<SimpleBoardResponseDto> simpleBoardResponseDtos = new ArrayList<SimpleBoardResponseDto>();
        List<Board> recommandBoards = getBoardList();
        for(Board recommandBoard:recommandBoards){
            SimpleBoardResponseDto simpleBoardResponseDto = new SimpleBoardResponseDto();
            simpleBoardResponseDto.setBoardInfo(recommandBoard);
            List<Tag> tags = getTagListByBoard(recommandBoard);
            simpleBoardResponseDto.setBoardTagListValue(tags);
            BoardTheme boardTheme = recommandBoard.getBoardTheme();
            List<MemoTheme> memoThemes = getMemoThemeListByBoardTheme(boardTheme);
            simpleBoardResponseDto.setThemeObject(boardTheme, memoThemes);
            simpleBoardResponseDtos.add(simpleBoardResponseDto);
        }
        return simpleBoardResponseDtos;
    }
}
