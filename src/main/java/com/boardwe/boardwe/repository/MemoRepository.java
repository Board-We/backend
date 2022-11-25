package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    void deleteByBoard(Board board);
}
