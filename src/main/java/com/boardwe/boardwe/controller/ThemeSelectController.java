package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.MemoThemeResponseDto;
import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.dto.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.service.ThemeSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeSelectController {

    private final ThemeSelectService themeSelectService;

    @GetMapping("/board-theme")
    public ResponseDto getBoardThemes() {
        List<BoardThemeSelectResponseDto> responseDtos = themeSelectService.getPublicThemes();
        return ResponseDto.ok("themes", responseDtos);
    }

    @GetMapping("/board/{boardCode}/memo-theme")
    public ResponseDto getMemoThemes(@PathVariable String boardCode) {
        List<MemoThemeResponseDto> responseDtos = themeSelectService.getMemoThemesOfBoard(boardCode);
        return ResponseDto.ok("memoThemesWithId", responseDtos);
    }
}
