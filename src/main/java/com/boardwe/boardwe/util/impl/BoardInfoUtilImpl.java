package com.boardwe.boardwe.util.impl;

import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BoardInfoUtilImpl implements BoardInfoUtil {

    private final TagRepository tagRepository;

    @Override
    public String getBoardLink(String boardCode) {
        return String.format("/board/%s/welcome", boardCode);
    }

    @Override
    public List<String> getBoardTags(Board board) {
        return tagRepository.findAllByBoardId(board.getId())
                .stream()
                .map(Tag::getValue)
                .toList();
    }
}
