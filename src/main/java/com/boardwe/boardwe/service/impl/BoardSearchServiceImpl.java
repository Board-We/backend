package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.BoardSearchService;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardSearchServiceImpl implements BoardSearchService {
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;

    private final ThemeUtil themeUtil;
    private final BoardInfoUtil boardInfoUtil;

    @Override
    public Page<BoardSearchResponseDto> searchBoardByTagWithPaging(String query, Pageable pageable) {
        List<BoardSearchResponseDto> searchResults = boardRepository.findAllByTagValue(query, pageable)
                .stream()
                .map(this::getBoardSearchResponseDto)
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), searchResults.size());
        return (searchResults.size() > 0) ?
            new PageImpl<>(searchResults.subList(start, end), pageable, searchResults.size()) :
            new PageImpl<>(searchResults, pageable, searchResults.size());
    }

    @Override
    public List<BoardSearchResponseDto> selectHotBoards() {
        return boardRepository.findTop10ByOpenTypeOrderByViewsDesc(OpenType.PUBLIC)
                .stream()
                .map(this::getBoardSearchResponseDto)
                .toList();
    }

    @Override
    public List<BoardSearchResponseDto> selectRecommendBoards() {
        return boardRepository.find10OpenBoardsOderByRandom()
                .stream()
                .map(this::getBoardSearchResponseDto)
                .toList();
    }

    private BoardSearchResponseDto getBoardSearchResponseDto(Board board) {
        List<String> tagValues = tagRepository.findAllByBoardId(board.getId())
                .stream()
                .map(Tag::getValue)
                .toList();
        return BoardSearchResponseDto.builder()
                .boardName(board.getName())
                .boardLink(boardInfoUtil.getBoardLink(board.getCode()))
                .boardViews(board.getViews())
                .boardTags(tagValues)
                .theme(themeUtil.getBoardThemeSelectResponseDto(board.getBoardTheme()))
                .build();
    }
}
