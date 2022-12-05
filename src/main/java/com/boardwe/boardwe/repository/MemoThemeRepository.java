package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.MemoTheme;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoThemeRepository extends JpaRepository<MemoTheme,Long> {
    List<MemoTheme> findByBoardThemeId(Long boardThemeId);
}
