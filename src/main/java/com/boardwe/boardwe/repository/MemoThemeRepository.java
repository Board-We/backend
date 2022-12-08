package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoThemeRepository extends JpaRepository<MemoTheme,Long> {
    Optional<MemoTheme> findByIdAndBoardTheme(Long id, BoardTheme boardTheme);

    List<MemoTheme> findByBoardThemeId(Long boardThemeId);
}
