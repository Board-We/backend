package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    void deleteByBoard(Board board);

    List<Memo> findByBoardAndContentContains(Board board,String query);

    Optional<Memo> findByIdAndBoard(Long id, Board board);

    List<Memo> findByBoardId(Long boardId);
}
