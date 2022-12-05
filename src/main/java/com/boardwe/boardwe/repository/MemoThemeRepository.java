package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.MemoTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoThemeRepository extends JpaRepository<MemoTheme,Long> {
    Optional<MemoTheme> findById(Long id);

    List<MemoTheme> findByBoardThemeId(Long boardThemeId);
}
