package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findAllByBoard(Board board);
}
