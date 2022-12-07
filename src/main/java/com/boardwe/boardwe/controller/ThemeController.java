package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("/board-themes")
    public ResponseEntity<List<BoardThemeSelectResponseDto>> getBoardThemes() {
        return ResponseEntity.ok(themeService.getPublicThemes());
    }

    @GetMapping("/board/{boardCode}/memo-themes")
    public ResponseEntity<List<MemoThemeSelectResponseDto>> getMemoThemes(@PathVariable String boardCode) {
        return ResponseEntity.ok(themeService.getMemoThemesOfBoard(boardCode));
    }
}
