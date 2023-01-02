package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.ThemeCategory;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.ThemeCategoryRepository;
import com.boardwe.boardwe.service.ThemeService;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThemeServiceImpl implements ThemeService {

    private final BoardRepository boardRepository;
    private final ThemeCategoryRepository categoryRepository;
    private final BoardThemeRepository boardThemeRepository;

    private final ThemeUtil themeUtil;

    @Override
    public List<BoardThemeSelectResponseDto> getPublicThemes() {
        log.info("[ThemeServiceImpl] Get public themes.");
        ThemeCategory userThemeCategory = themeUtil.getUserThemeCategory();
        List<ThemeCategory> themeCategories = categoryRepository.findByNameNot(userThemeCategory.getName());

        List<BoardThemeSelectResponseDto> boardThemeSelectResponseDtos = new ArrayList<>();
        for (ThemeCategory themeCategory : themeCategories) {
            List<BoardTheme> boardThemes = boardThemeRepository.findByThemeCategoryId(themeCategory.getId());
            for (BoardTheme boardTheme : boardThemes) {
                boardThemeSelectResponseDtos.add(
                        BoardThemeSelectResponseDto.builder()
                                .id(boardTheme.getId())
                                .name(boardTheme.getName())
                                .category(themeCategory.getName())
                                .boardBackgroundType(boardTheme.getBackgroundType())
                                .boardBackground(themeUtil.getBackgroundValue(boardTheme))
                                .boardFont(boardTheme.getFont())
                                .memoThemes(themeUtil.getMemoThemeSelectResponseDtos(boardTheme))
                                .build());
            }
        }

        return boardThemeSelectResponseDtos;
    }

    @Override
    public List<MemoThemeSelectResponseDto> getMemoThemesOfBoard(String boardCode) {
        log.info("[ThemeServiceImpl] Get memo themes of board (code: {}).", boardCode);
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        return themeUtil.getMemoThemeSelectResponseDtos(board.getBoardTheme());
    }

}
