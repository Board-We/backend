package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardMemoSearchResponseDto;
import com.boardwe.boardwe.dto.inner.BoardThemeSearchResponseDto;
import com.boardwe.boardwe.dto.inner.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.inner.MemoThemesWithIdResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.BoardMemoSearchService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardMemoSearchServiceImpl implements BoardMemoSearchService {

    private final BoardRepository boardRepository;
    private final MemoRepository memoRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public BoardMemoSearchResponseDto searchMemo(String boardCode, String query) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        List<Memo> memos = memoRepository.findByBoardAndContentContains(board, query);


        BoardTheme boardTheme = board.getBoardTheme();

        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        BoardThemeSearchResponseDto boardThemeDto = BoardThemeSearchResponseDto.builder()
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemesWithId(getMemoThemeResponseDtos(boardTheme))
                .build();

        List<MemoSearchResponseDto> memoDtos = new ArrayList<>();
        for (Memo memo : memos) {
            MemoSearchResponseDto memoDto = MemoSearchResponseDto.builder()
                    .memoThemeId(memo.getMemoTheme().getId())
                    .memoContent(memo.getContent())
                    .build();
            memoDtos.add(memoDto);
        }

        return BoardMemoSearchResponseDto.builder()
                .theme(boardThemeDto)
                .memos(memoDtos)
                .build();

    }
    private List<MemoThemesWithIdResponseDto> getMemoThemeResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemesWithIdResponseDto> memoThemeSelectResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                    memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
            memoThemeSelectResponseDtos.add(MemoThemesWithIdResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(memoBackgroundValue)
                    .memoTextColor(memoTheme.getTextColor())
                    .build());
        }
        return memoThemeSelectResponseDtos;
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}
