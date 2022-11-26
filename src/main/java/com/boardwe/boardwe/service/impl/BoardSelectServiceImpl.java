package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardSelectResponseDto;
import com.boardwe.boardwe.dto.inner.BoardThemeResponseDto;
import com.boardwe.boardwe.dto.inner.MemoSelectResponseDto;
import com.boardwe.boardwe.dto.inner.MemoThemeResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.BoardBeforeWritingException;
import com.boardwe.boardwe.exception.custom.BoardClosedException;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.BoardSelectService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardSelectServiceImpl implements BoardSelectService {

    private final BoardRepository boardRepository;
    private final MemoRepository memoRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public BoardSelectResponseDto getBoard(String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());

        validateBoardStatus(boardStatus);

        return BoardSelectResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .openType(board.getOpenType())
                .boardStatus(boardStatus)
                .theme(getThemeResponseDto(board.getBoardTheme()))
                .memos(boardStatus == BoardStatus.OPEN? getMemoResponseDtos(board) : null)
                .build();
    }

    private List<MemoSelectResponseDto> getMemoResponseDtos(Board board) {
        List<Memo> memos = memoRepository.findByBoardId(board.getId());
        List<MemoSelectResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo : memos) {
            memoResponseDtos.add(MemoSelectResponseDto.builder()
                    .memoThemeId(memo.getMemoTheme().getId())
                    .memoContent(memo.getContent())
                    .build());
        }
        return memoResponseDtos;
    }

    private BoardThemeResponseDto getThemeResponseDto(BoardTheme boardTheme) {
        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        return BoardThemeResponseDto.builder()
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemesWithId(getMemoThemeResponseDtos(boardTheme))
                .build();
    }

    private List<MemoThemeResponseDto> getMemoThemeResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeResponseDto> memoThemeResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                    memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
            memoThemeResponseDtos.add(MemoThemeResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(memoBackgroundValue)
                    .memoTextColor(memoTheme.getTextColor())
                    .build());
        }
        return memoThemeResponseDtos;
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }

    private static void validateBoardStatus(BoardStatus boardStatus) {
        if (boardStatus == BoardStatus.BEFORE_WRITING) {
            throw new BoardBeforeWritingException();
        } else if (boardStatus == BoardStatus.BEFORE_OPEN) {
            throw new BoardBeforeOpenException();
        } else if (boardStatus == BoardStatus.CLOSED) {
            throw new BoardClosedException();
        }
    }
}
