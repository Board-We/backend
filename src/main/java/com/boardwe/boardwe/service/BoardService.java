package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.Tag;

public interface BoardService {
    public abstract Board getBoardByBoardcode(String boardCode);
    public abstract List<Tag> getTagListByBoard(Board board);
    public abstract List<Memo> getMemoListByBoard(Board board);
    public abstract BoardTheme getBoardThemeById(Long boardThemeId);
}
