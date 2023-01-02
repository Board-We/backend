package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.res.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.MemoThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.*;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.MemoService;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
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
        log.info("[MemoServiceImpl] Create memo (content: {}).", memoCreateRequestDto.getMemoContent());
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

        log.info("[MemoServiceImpl] Memo attached at board ({}).", board.getName());
        return MemoCreateResponseDto.builder()
                .openStartTime(board.getOpenStartTime())
                .build();
    }

    @Override
    @Transactional
    public void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode) {
        log.info("[MemoServiceImpl] Delete memos (ids: {}).", memoDeleteRequestDto.getMemoIds());
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
        log.info("[MemoServiceImpl] Search memos (query: {}).", query);
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
            log.error("[MemoServiceImpl] Board cannot write.");
            throw new BoardCannotWriteException();
        }
    }

    private void validateBoardIsOpened(BoardStatus boardStatus) {
        if (boardStatus != BoardStatus.OPEN) {
            log.error("[MemoServiceImpl] Board not opening.");
            throw new BoardNotOpenedException();
        }
    }

    @Override
    public List<MemoSelectResponseDto> getMemo(String boardCode, String password) {
        log.info("[MemoServiceImpl] Get memos of board (code: {}).", boardCode);
        Board board = boardRepository.findByCode(boardCode)
        .orElseThrow(BoardNotFoundException::new);

        if (board.getOpenType() == OpenType.PRIVATE && !Objects.equals(board.getPassword(), password)){
            log.error("[MemoServiceImpl] Password is incorrect.");
            throw new InvalidPasswordException();
        }

        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(board.getWritingStartTime())){
            throw new BoardBeforeWritingException();
        }
        else if(now.isBefore(board.getWritingEndTime())){
            throw new BoardWritingException();
        }
        else if(now.isBefore(board.getOpenStartTime())){
            throw new BoardBeforeOpenException();
        }
        else if(now.isAfter(board.getOpenEndTime())) {
            throw new BoardClosedException();
        }

        return memoRepository.findByBoardId(board.getId())
                .stream()
                .map(memo -> MemoSelectResponseDto.builder()
                        .memoId(memo.getId())
                        .memoThemeId(memo.getMemoTheme().getId())
                        .memoContent(memo.getContent())
                        .build())
                .toList();
    }
}
