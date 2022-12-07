package com.boardwe.boardwe.util;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.entity.Board;

import java.util.List;

public interface BoardInfoUtil {
    String getBoardLink(String boardCode);
    List<String> getBoardTags(Board board);
}
