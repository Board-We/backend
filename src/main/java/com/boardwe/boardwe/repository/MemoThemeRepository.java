package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.MemoTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoThemeRepository extends JpaRepository<MemoTheme,Long> {
    List<MemoTheme> findByBoardThemeId(Long boardThemeId);
}
