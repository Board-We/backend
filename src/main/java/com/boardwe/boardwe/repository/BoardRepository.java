package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByCode(String boardCode);
}
