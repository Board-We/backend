package com.boardwe.boardwe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardwe.boardwe.dto.WelcomeBoardResponseDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.service.WelcomeBoardService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WelcomeBoardServiceImpl implements WelcomeBoardService{
    private final BoardRepository boardRepository;
    private final MemoRepository memoRepository;
    private final TagRepository tagRepository;

    @Override
    public WelcomeBoardResponseDto getWelcomBoardDto(String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);
        BoardTheme boardTheme = board.getBoardTheme();
        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
            board.getWritingStartTime(),
            board.getWritingEndTime(),
            board.getOpenStartTime(),
            board.getOpenEndTime()
        );

        return WelcomeBoardResponseDto.builder()
            .boardName(board.getName())
            .boradDescription(board.getDescription())
            .tags(getTagValues(board.getId()))
            .memoCnt(getMemoCnt(board.getId()))
            .boardViews(board.getViews())
            .writingStartTime(board.getWritingStartTime())
            .writingEndTime(board.getWritingEndTime())
            .openStartTime(board.getOpenStartTime())
            .openEndTime(board.getOpenEndTime())
            .boardStatus(boardStatus)
            .boardBackgroundType(boardTheme.getBackgroundType())
            .boardBackground(
                boardTheme.getBackgroundType() == BackgroundType.COLOR ?
                    boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo())
            )
            .boardFont(board.getBoardTheme().getFont())
            .build();
    }
    
    private List<String> getTagValues(Long boardId){
        List<String> tagValues = new ArrayList<String>();
        List<Tag> tags = tagRepository.findAllByBoardId(boardId);
        for(Tag tag:tags){
            tagValues.add(tag.getValue());
        }
        return tagValues;
    }

    private int getMemoCnt(Long boardId){
        return memoRepository.findByBoardId(boardId).size();
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}
