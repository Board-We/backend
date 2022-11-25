package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.MemoDeleteRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoWithInvalidBoardException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.service.MemoDeleteService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoDeleteServiceImpl implements MemoDeleteService {

    private final MemoRepository memoRepository;

    private final BoardRepository boardRepository;
    @Override
    public void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);
        /**
         * TODO
         * Memo & Board Fetch Join 필요
         * Session ID 적용 필요
         */
        for (Long memoId : memoDeleteRequestDto.getMemoIds()) {
            Memo memo = memoRepository.findById(memoId).orElseThrow(MemoNotFoundException::new);

            if(Objects.equals(memo.getBoard().getCode(), boardCode)){
                memoRepository.deleteById(memoId);
            }else{
                throw new MemoWithInvalidBoardException();
            }

        }
    }
}
