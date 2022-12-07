package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.MemoWithInvalidBoardException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.MemoRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.service.MemoService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final BoardRepository boardRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    @Transactional
    public MemoCreateResponseDto createMemo(MemoCreateRequestDto memoCreateRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        MemoTheme memoTheme = memoThemeRepository.findById(memoCreateRequestDto.getMemoThemeId()).orElseThrow(MemoThemeNotFoundException::new);

        Memo memo = Memo.builder()
                .board(board)
                .memoTheme(memoTheme)
                .content(memoCreateRequestDto.getMemoContent())
                .build();
        memoRepository.save(memo);

        return MemoCreateResponseDto.builder().openStartTime(board.getOpenStartTime()).build();
    }

    @Override
    @Transactional
    public void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);
        /**
         * TODO
         * Memo & Board Fetch Join 필요
         * Session ID 적용 필요
         */
        for (Long memoId : memoDeleteRequestDto.getMemoIds()) {
            Memo memo = memoRepository.findById(memoId).orElseThrow(MemoNotFoundException::new);

            if(Objects.equals(memo.getBoard().getCode(), boardCode)){
                memoRepository.deleteById(memoId);
            }else{
                throw new MemoWithInvalidBoardException();
            }

        }
    }

    @Override
    public MemoSearchResponseDto searchMemo(String boardCode, String query) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        List<Memo> memos = memoRepository.findByBoardAndContentContains(board, query);


        BoardTheme boardTheme = board.getBoardTheme();

        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        BoardThemeSelectResponseDto boardThemeDto = BoardThemeSelectResponseDto.builder()
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeResponseDtos(boardTheme))
                .build();

        List<MemoSelectResponseDto> memoDtos = new ArrayList<>();
        for (Memo memo : memos) {
            MemoSelectResponseDto memoDto = MemoSelectResponseDto.builder()
                    .memoThemeId(memo.getMemoTheme().getId())
                    .memoContent(memo.getContent())
                    .build();
            memoDtos.add(memoDto);
        }

        return MemoSearchResponseDto.builder()
                .theme(boardThemeDto)
                .memos(memoDtos)
                .build();
    }

    private List<MemoThemeSelectResponseDto> getMemoThemeResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeSelectResponseDto> memoThemeSelectResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                    memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
            memoThemeSelectResponseDtos.add(MemoThemeSelectResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(memoBackgroundValue)
                    .memoTextColor(memoTheme.getTextColor())
                    .build());
        }
        return memoThemeSelectResponseDtos;
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}