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
import com.boardwe.boardwe.service.BoardListService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.OpenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardListServiceImpl implements BoardListService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public List<BoardSearchResultResponseDto> selectHotBoards() {
        List<Board> boards = boardRepository.findTop10ByOpenTypeOrderByViewsDesc(OpenType.PUBLIC);
        return getBoardSearchResultResponseDtos(boards);
    }

    @Override
    public List<BoardSearchResultResponseDto> selectRecommendBoards() {
        List<Board> boards = boardRepository.find10OpenBoardsOderByRandom();
        return getBoardSearchResultResponseDtos(boards);
    }

    private List<BoardSearchResultResponseDto> getBoardSearchResultResponseDtos(List<Board> boards) {
        List<BoardSearchResultResponseDto> responseDtos = new ArrayList<>();
        for (Board board : boards) {
            BoardSearchResultResponseDto boardSearchResultResponseDto = BoardSearchResultResponseDto.builder()
                    .boardName(board.getName())
                    .boardLink(getBoardLink(board.getCode()))
                    .boardViews(board.getViews())
                    .boardTags(getTagValues(board.getId()))
                    .theme(getThemeResponseDto(board.getBoardTheme()))
                    .build();
            responseDtos.add(boardSearchResultResponseDto);
        }
        return responseDtos;
    }

    private BoardThemeSelectResponseDto getThemeResponseDto(BoardTheme boardTheme) {
        return BoardThemeSelectResponseDto.builder()
                .boardBackground(getBackground(boardTheme))
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeResponseDtos(boardTheme.getId()))
                .build();
    }

    private List<MemoThemeSelectResponseDto> getMemoThemeResponseDtos(Long boardThemeId) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardThemeId);
        List<MemoThemeSelectResponseDto> memoThemeResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            MemoThemeSelectResponseDto memoThemeResponseDto = MemoThemeSelectResponseDto.builder()
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(getBackground(memoTheme))
                    .memoTextColor(memoTheme.getTextColor())
                    .build();
            memoThemeResponseDtos.add(memoThemeResponseDto);
        }
        return memoThemeResponseDtos;
    }

    private String getBoardLink(String boardCode) {
        return String.format("/board/%s/welcome", boardCode);
    }

    private List<String> getTagValues(Long boardId) {
        List<Tag> tags = tagRepository.findAllByBoardId(boardId);
        List<String> tagValues = new ArrayList<>();
        for (Tag tag : tags) {
            tagValues.add(tag.getValue());
        }
        return tagValues;
    }

    private String getBackground(BoardTheme boardTheme) {
        if (boardTheme.getBackgroundType() == BackgroundType.COLOR) {
            return boardTheme.getBackgroundColor();
        } else {
            return String.format("/image/%s", boardTheme.getBackgroundImageInfo().getUuid());
        }
    }


    private String getBackground(MemoTheme memoTheme) {
        if (memoTheme.getBackgroundType() == BackgroundType.COLOR) {
            return memoTheme.getBackgroundColor();
        } else {
            return String.format("/image/%s", memoTheme.getBackgroundImageInfo().getUuid());
        }
    }
}
