package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardSearchResultResponseDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardSearchServiceImpl implements BoardSearchService{
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public Page<BoardSearchResultResponseDto> getBoardSearchResultPage(String query, Pageable pageable) {
        List<BoardSearchResultResponseDto> boards = new ArrayList<BoardSearchResultResponseDto>();
        List<Board> searchResult = boardRepository.findAllByTagValue(query, pageable);
        for(Board board:searchResult){
            BoardSearchResultResponseDto boardSearchResultResponseDto = BoardSearchResultResponseDto.builder()
                .boardName(board.getName())
                .boardLink(getBoardLink(board.getCode()))
                .boardViews(board.getViews())
                .boardTags(getTagValues(board.getId()))
                .theme(getThemeResponseDto(board.getBoardTheme()))
            .build();
            boards.add(boardSearchResultResponseDto);
        }
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > boards.size() ? boards.size() : (start + pageable.getPageSize());
        Page<BoardSearchResultResponseDto> resultPage = new PageImpl<>(boards.subList(start, end), pageable, boards.size());
        return resultPage;
    }
    
    private BoardThemeSelectResponseDto getThemeResponseDto(BoardTheme boardTheme){
        return BoardThemeSelectResponseDto.builder()
            .boardBackground(getBackground(boardTheme))
            .boardBackgroundType(boardTheme.getBackgroundType())
            .boardFont(boardTheme.getFont())
            .memoThemes(getMemoThemeResponseDtos(boardTheme.getId()))
        .build();
    }

    private List<MemoThemeSelectResponseDto> getMemoThemeResponseDtos(Long boardThemeId){
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardThemeId);
        List<MemoThemeSelectResponseDto> memoThemeResponseDtos = new ArrayList<>();
        for(MemoTheme memoTheme:memoThemes){
            MemoThemeSelectResponseDto memoThemeResponseDto = MemoThemeSelectResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
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
