package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.BoardDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDeleteServiceImpl implements BoardDeleteService {

    private final BoardRepository boardRepository;

    private final MemoRepository memoRepository;

    private final TagRepository tagRepository;
    @Override
    public void deleteBoard(BoardDeleteRequestDto requestDto,String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        if(Objects.equals(board.getPassword(), requestDto.getPassword())){

            memoRepository.deleteByBoard(board);
            tagRepository.deleteByBoard(board);

            boardRepository.delete(board);
        }

    }
}
