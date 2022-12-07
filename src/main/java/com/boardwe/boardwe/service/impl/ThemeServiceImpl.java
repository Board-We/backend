package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.ThemeCategoryRepository;
import com.boardwe.boardwe.service.ThemeService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThemeServiceImpl implements ThemeService {

    private final BoardRepository boardRepository;
    private final ThemeCategoryRepository categoryRepository;
    private final BoardThemeRepository boardThemeRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public List<BoardThemeSelectResponseDto> getPublicThemes() {
        List<BoardThemeSelectResponseDto> boardThemeSelectResponseDtos = new ArrayList<>();
        List<ThemeCategory> themeCategories = categoryRepository.findByNameNot("TEMP");

        for (ThemeCategory themeCategory : themeCategories) {
            List<BoardTheme> boardThemes = boardThemeRepository.findByThemeCategoryId(themeCategory.getId());
            for (BoardTheme boardTheme : boardThemes) {
                boardThemeSelectResponseDtos.add(getThemeResponseDto(boardTheme, themeCategory.getName()));
            }
        }

        return boardThemeSelectResponseDtos;
    }

    @Override
    public List<MemoThemeSelectResponseDto> getMemoThemesOfBoard(String boardCode) {
        List<MemoThemeSelectResponseDto> memoThemeSelectResponseDtos = new ArrayList<>();
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(board.getBoardTheme().getId());
        for (MemoTheme memoTheme : memoThemes) {
            memoThemeSelectResponseDtos.add(getMemoThemeSelectResponseDto(memoTheme));
        }
        return memoThemeSelectResponseDtos;
    }

    private BoardThemeSelectResponseDto getThemeResponseDto(BoardTheme boardTheme, String category) {
        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        return BoardThemeSelectResponseDto.builder()
                .id(boardTheme.getId())
                .name(boardTheme.getName())
                .category(category)
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeSelectResponseDtos(boardTheme))
                .build();
    }

    private List<MemoThemeSelectResponseDto> getMemoThemeSelectResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeSelectResponseDto> memoThemeSelectResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            memoThemeSelectResponseDtos.add(getMemoThemeSelectResponseDto(memoTheme));
        }
        return memoThemeSelectResponseDtos;
    }

    private MemoThemeSelectResponseDto getMemoThemeSelectResponseDto(MemoTheme memoTheme) {
        String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
        return MemoThemeSelectResponseDto.builder()
                .memoThemeId(memoTheme.getId())
                .memoBackgroundType(memoTheme.getBackgroundType())
                .memoBackground(memoBackgroundValue)
                .memoTextColor(memoTheme.getTextColor())
                .build();
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}
