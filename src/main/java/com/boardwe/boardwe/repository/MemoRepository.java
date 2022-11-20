package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByBoardId(Long boardId);
}
