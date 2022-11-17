package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
}
