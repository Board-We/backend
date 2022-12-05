package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.BoardTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardThemeRepository extends JpaRepository<BoardTheme,Long> {
    List<BoardTheme> findByThemeCategoryId(Long themeCategoryId);
}
