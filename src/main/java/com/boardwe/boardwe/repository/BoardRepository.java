package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.type.OpenType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByCode(String boardCode);

    List<Board> findAllByOpenType(OpenType openType, Pageable pageable);

    @Query(value = "select * from Board where Board.board_open_type='PUBLIC' order by RAND() limit 10", nativeQuery = true)
    List<Board> find10RandomOpenBoards();

    @Query("select b from Board b, Tag t where t.value=?1 and t.board.id=b.id")
    Page<Board> findAllByTagValue(String query, Pageable pageable);
}
