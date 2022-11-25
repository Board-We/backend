package com.boardwe.boardwe.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.WelcomeBoardResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
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
    public WelcomeBoardResponseDto getWelcomBoardDto(String boardCode){
        WelcomeBoardResponseDto welcomBoardResponseDto = new WelcomeBoardResponseDto();
        Board targetBoard = getBoardByBoardcode(boardCode);
        welcomBoardResponseDto.setBoardInfo(targetBoard);
        welcomBoardResponseDto.setTagListValue(getTagListByBoard(targetBoard));
        welcomBoardResponseDto.setMemoCnt(getMemoListByBoard(targetBoard).size());
        welcomBoardResponseDto.setBoardThemeInfo(targetBoard.getBoardTheme());
        return welcomBoardResponseDto;
    }
}
