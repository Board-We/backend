package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardSearchResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.BoardSearchService;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
        log.info("[BoardServiceServiceImpl] Search boards with tag (query: {}).", query);
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
        log.info("[BoardServiceServiceImpl] Get 10 Hottest boards.");
        return boardRepository.findTop10ByOpenTypeOrderByViewsDesc(OpenType.PUBLIC)
                .stream()
                .map(this::getBoardSearchResponseDto)
                .toList();
    }

    @Override
    public List<BoardSearchResponseDto> selectRecommendBoards() {
        log.info("[BoardServiceServiceImpl] Get 10 recommended boards.");
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
        BoardTheme boardTheme = board.getBoardTheme();
        return BoardSearchResponseDto.builder()
                .boardName(board.getName())
                .boardLink(boardInfoUtil.getBoardLink(board.getCode()))
                .boardViews(board.getViews())
                .openStartTime(board.getOpenStartTime())
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .boardTags(tagValues)
                .boardFont(boardTheme.getFont())
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(themeUtil.getBackgroundValue(boardTheme))
                .build();
    }
}
