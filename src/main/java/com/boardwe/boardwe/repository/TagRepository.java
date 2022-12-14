package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {

    void deleteByBoard(Board board);
    List<Tag> findAllByBoardId(Long boardId);
}
