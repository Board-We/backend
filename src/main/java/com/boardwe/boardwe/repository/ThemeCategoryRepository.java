package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.ThemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory,Long> {
    List<ThemeCategory> findByName(String name);
    List<ThemeCategory> findByNameNot(String name);
}
