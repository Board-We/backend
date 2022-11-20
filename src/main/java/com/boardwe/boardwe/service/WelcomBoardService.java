package com.boardwe.boardwe.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.welcomBoardDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;

@Service
public class WelcomBoardService implements BoardService{
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    BoardThemeRepository boardThemeRepository;

    @Override
    public Board getBoardByBoardcode(String boardCode){
        Optional<Board> optionalBoard = boardRepository.findByCode(boardCode);
        return optionalBoard.orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Tag> getTagListByBoard(Board board){
        return tagRepository.findByBoard(board);
    }

    @Override
    public List<Memo> getMemoListByBoard(Board board){
        return memoRepository.findByBoard(board);
    }

    @Override
    public BoardTheme getBoardThemeById(Long boardThemeId){
        Optional<BoardTheme> optionalBoardTheme = boardThemeRepository.findById(boardThemeId);
        return optionalBoardTheme.orElseThrow(() -> new NoSuchElementException());
    }

    public welcomBoardDto getWelcomBoardDto(String boardCode){
        welcomBoardDto welcomBoardDto = new welcomBoardDto();
        Board targetBoard = getBoardByBoardcode(boardCode);
        welcomBoardDto.setBoardInfo(targetBoard);
        welcomBoardDto.setTagListValue(getTagListByBoard(targetBoard));
        welcomBoardDto.setMemoCnt(getMemoListByBoard(targetBoard).size());
        return welcomBoardDto;
    }
}
