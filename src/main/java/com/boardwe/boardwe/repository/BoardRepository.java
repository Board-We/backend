package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
