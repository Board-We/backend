package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.type.OpenType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByCode(String boardCode);

    List<Board> findAllByOpenType(OpenType openType, Pageable pageable);
}
