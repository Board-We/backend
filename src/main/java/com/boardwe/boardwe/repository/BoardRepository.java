package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByCode(String code);
}
