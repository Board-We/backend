package com.boardwe.boardwe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.ListElementDtos.MemoThemeResponseDto;
import com.boardwe.boardwe.dto.ListElementDtos.SimpleBoardResponseDto;
import com.boardwe.boardwe.dto.ListElementDtos.ThemeResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.BoardSearchService;
import com.boardwe.boardwe.type.BackgroundType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardSearchServiceImpl implements BoardSearchService{
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public Page<SimpleBoardResponseDto> getBoardSearchResultPage(String query, Pageable pageable) {
        List<SimpleBoardResponseDto> boards = new ArrayList<SimpleBoardResponseDto>();
        List<Board> searchResult = boardRepository.findAllByTagValue(query, pageable);
        for(Board board:searchResult){
            SimpleBoardResponseDto simpleBoardResponseDto = SimpleBoardResponseDto.builder()
                .boardName(board.getName())
                .boardLink(getBoardLink(board.getCode()))
                .boardViews(board.getViews())
                .boardTags(getTagValues(board.getId()))
                .theme(getThemeResponseDto(board.getBoardTheme()))
            .build();
            boards.add(simpleBoardResponseDto);
        }
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > boards.size() ? boards.size() : (start + pageable.getPageSize());
        Page<SimpleBoardResponseDto> resultPage = new PageImpl<>(boards.subList(start, end), pageable, boards.size());
        return resultPage;
    }
    
    private ThemeResponseDto getThemeResponseDto(BoardTheme boardTheme){
        return ThemeResponseDto.builder()
            .boardBackground(getBackground(boardTheme))
            .boardBackgroundType(boardTheme.getBackgroundType())
            .boardFont(boardTheme.getFont())
            .memoTheme(getMemoThemeResponseDtos(boardTheme.getId()))
        .build();
    }

    private List<MemoThemeResponseDto> getMemoThemeResponseDtos(Long boardThemeId){
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardThemeId);
        List<MemoThemeResponseDto> memoThemeResponseDtos = new ArrayList<MemoThemeResponseDto>();
        for(MemoTheme memoTheme:memoThemes){
            MemoThemeResponseDto memoThemeResponseDto = MemoThemeResponseDto.builder()
                .memoBackgroundType(memoTheme.getBackgroundType())
                .memoBackground(getBackground(memoTheme))
                .memoTextColor(memoTheme.getTextColor())
            .build();
            memoThemeResponseDtos.add(memoThemeResponseDto);
        }
        return memoThemeResponseDtos;
    }

    private String getBoardLink(String boardCode){
        return "/board/" + boardCode + "/welcome";
    }

    private List<String> getTagValues(Long boardId){
        List<Tag> tags = tagRepository.findAllByBoardId(boardId);
        List<String> tagValues = new ArrayList<String>();
        for(Tag tag : tags){
            tagValues.add(tag.getValue());
        }
        return tagValues;
    }

    private String getBackground(BoardTheme boardTheme){
        if(boardTheme.getBackgroundType() == BackgroundType.COLOR){
            return boardTheme.getBackgroundColor();
        }
        else{
            return String.format("/image/%s", boardTheme.getBackgroundImageInfo().getUuid());
        }
    }

    
    private String getBackground(MemoTheme memoTheme){
        if(memoTheme.getBackgroundType() == BackgroundType.COLOR){
            return memoTheme.getBackgroundColor();
        }
        else{
            return String.format("/image/%s", memoTheme.getBackgroundImageInfo().getUuid());
        }
    }
}
