package com.boardwe.boardwe.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;

public interface BoardSearchService {
    public abstract List<Board> getBoardList(Pageable pageable, String query);
    public abstract List<Tag> getTagListByBoard(Board board);
    public abstract List<MemoTheme> getMemoThemeListByBoardTheme(BoardTheme boardTheme);
}
