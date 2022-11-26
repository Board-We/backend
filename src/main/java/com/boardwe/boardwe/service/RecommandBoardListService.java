package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;

public interface RecommandBoardListService {
    public abstract List<Board> getBoardList();
    public abstract List<Tag> getTagListByBoard(Board board);
    public abstract List<MemoTheme> getMemoThemeListByBoardTheme(BoardTheme boardTheme);
}
