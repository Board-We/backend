package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.service.ThemeSelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeSelectController {

    private final ThemeSelectService themeSelectService;

    @GetMapping("/board-themes")
    public ResponseEntity<List<BoardThemeSelectResponseDto>> getBoardThemes() {
        return ResponseEntity.ok(themeSelectService.getPublicThemes());
    }

    @GetMapping("/board/{boardCode}/memo-themes")
    public ResponseEntity<List<MemoThemeSelectResponseDto>> getMemoThemes(@PathVariable String boardCode) {
        return ResponseEntity.ok(themeSelectService.getMemoThemesOfBoard(boardCode));
    }
}
