package com.boardwe.boardwe.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.WelcomeBoardDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;

@Service
public class WelcomeBoardServiceImpl implements WelcomeBoardService{
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private BoardThemeRepository boardThemeRepository;

    @Override
    public Board getBoardByBoardcode(String boardCode){
        Optional<Board> optionalBoard = boardRepository.findByCode(boardCode);
        return optionalBoard.orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Tag> getTagListByBoard(Board board){
        return tagRepository.findAllByBoard(board);
    }

    @Override
    public List<Memo> getMemoListByBoard(Board board){
        return memoRepository.findAllByBoard(board);
    }

    @Override
    public BoardTheme getBoardThemeById(Long boardThemeId){
        Optional<BoardTheme> optionalBoardTheme = boardThemeRepository.findById(boardThemeId);
        return optionalBoardTheme.orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public WelcomeBoardDto getWelcomBoardDto(String boardCode){
        WelcomeBoardDto welcomBoardDto = new WelcomeBoardDto();
        Board targetBoard = getBoardByBoardcode(boardCode);
        BoardTheme targetBoardTheme = getBoardThemeById(targetBoard.getBoardTheme().getId());
        welcomBoardDto.setBoardInfo(targetBoard);
        welcomBoardDto.setTagListValue(getTagListByBoard(targetBoard));
        welcomBoardDto.setMemoCnt(getMemoListByBoard(targetBoard).size());
        welcomBoardDto.setBoardThemeInfo(targetBoardTheme);
        return welcomBoardDto;
    }
}
