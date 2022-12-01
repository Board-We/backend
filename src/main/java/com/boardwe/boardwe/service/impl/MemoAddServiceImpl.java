package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.MemoAddRequestDto;
import com.boardwe.boardwe.dto.MemoAddResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoThemeNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.MemoAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoAddServiceImpl implements MemoAddService {

    private final MemoRepository memoRepository;
    private final BoardRepository boardRepository;
    private final MemoThemeRepository memoThemeRepository;
    @Override
    public MemoAddResponseDto addMemo(MemoAddRequestDto memoAddRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        MemoTheme memoTheme = memoThemeRepository.findById(memoAddRequestDto.getMemoThemeId()).orElseThrow(MemoThemeNotFoundException::new);

        Memo memo = Memo.builder()
                .board(board)
                .memoTheme(memoTheme)
                .content(memoAddRequestDto.getMemoContent())
                .build();
        memoRepository.save(memo);

        return MemoAddResponseDto.builder().openStartTime(board.getOpenStartTime()).build();
    }
}
