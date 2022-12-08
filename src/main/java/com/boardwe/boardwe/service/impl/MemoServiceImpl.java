package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.BoardCannotWriteException;
import com.boardwe.boardwe.exception.custom.other.BoardNotOpenedException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.MemoService;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final BoardRepository boardRepository;
    private final MemoThemeRepository memoThemeRepository;

    private final ThemeUtil themeUtil;

    @Override
    @Transactional
    public MemoCreateResponseDto createMemo(MemoCreateRequestDto memoCreateRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        validateBoardCanWrite(getBoardStatus(board));
        MemoTheme memoTheme = memoThemeRepository.findByIdAndBoardTheme(
                        memoCreateRequestDto.getMemoThemeId(), board.getBoardTheme())
                .orElseThrow(MemoThemeNotFoundException::new);

        memoRepository.save(Memo.builder()
                .board(board)
                .memoTheme(memoTheme)
                .content(memoCreateRequestDto.getMemoContent())
                .build());

        return MemoCreateResponseDto.builder()
                .openStartTime(board.getOpenStartTime())
                .build();
    }

    @Override
    @Transactional
    public void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        for (Long memoId : memoDeleteRequestDto.getMemoIds()) {
            memoRepository.findByIdAndBoard(memoId, board)
                    .orElseThrow(MemoNotFoundException::new);
            memoRepository.deleteById(memoId);
        }
    }

    @Override
    public MemoSearchResponseDto searchMemo(String boardCode, String query) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        validateBoardIsOpened(getBoardStatus(board));
        BoardTheme boardTheme = board.getBoardTheme();
        BoardThemeSelectResponseDto boardThemeDto = themeUtil.getBoardThemeSelectResponseDto(boardTheme);

        List<MemoSelectResponseDto> memoDtos = memoRepository.findByBoardAndContentContains(board, query)
                .stream()
                .map(memo -> MemoSelectResponseDto.builder()
                        .memoThemeId(memo.getMemoTheme().getId())
                        .memoContent(memo.getContent())
                        .build())
                .toList();

        return MemoSearchResponseDto.builder()
                .theme(boardThemeDto)
                .memos(memoDtos)
                .build();
    }

    private BoardStatus getBoardStatus(Board board) {
        return BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());
    }

    private void validateBoardCanWrite(BoardStatus boardStatus) {
        if (boardStatus != BoardStatus.WRITING) {
            throw new BoardCannotWriteException();
        }
    }

    private void validateBoardIsOpened(BoardStatus boardStatus) {
        if (boardStatus != BoardStatus.OPEN) {
            throw new BoardNotOpenedException();
        }
    }
}
