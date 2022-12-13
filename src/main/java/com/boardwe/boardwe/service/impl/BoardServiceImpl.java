package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.req.inner.BoardThemeCreateRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoColorAndTextColorRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoImageAndTextColorRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.dto.res.WelcomeBoardResponseDto;
import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.BoardThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.BoardBeforeOpenException;
import com.boardwe.boardwe.exception.custom.other.BoardBeforeWritingException;
import com.boardwe.boardwe.exception.custom.other.BoardClosedException;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.FileUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import com.boardwe.boardwe.vo.ImageInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardThemeRepository boardThemeRepository;
    private final ImageInfoRepository imageInfoRepository;
    private final MemoThemeRepository memoThemeRepository;
    private final MemoRepository memoRepository;

    private final BoardInfoUtil boardInfoUtil;
    private final ThemeUtil themeUtil;
    private final FileUtil fileUtil;

    @Override
    @Transactional
    public BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Board board = boardRepository.save(
                Board.builder()
                        .boardTheme(getBoardTheme(requestDto))
                        .code(UUID.randomUUID().toString())
                        .name(requestDto.getBoardName())
                        .description(requestDto.getBoardDescription())
                        .writingStartTime(requestDto.getWritingStartTime())
                        .writingEndTime(requestDto.getWritingEndTime())
                        .openStartTime(requestDto.getOpenStartTime())
                        .openEndTime(requestDto.getOpenEndTime())
                        .password(requestDto.getPassword())
                        .openType(requestDto.getOpenType())
                        .views(0)
                        .build());

        for (String tag : requestDto.getTags()) {
            tagRepository.save(
                    Tag.builder()
                            .board(board)
                            .value(tag)
                            .build());
        }

        return BoardCreateResponseDto.builder()
                .boardLink(boardInfoUtil.getBoardLink(board.getCode()))
                .build();
    }

    @Override
    public BoardReadResponseDto readBoard(String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());

        validateBoardStatus(boardStatus);

        saveBoardWithIncreaseViews(board);

        return BoardReadResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .openType(board.getOpenType())
                .boardStatus(boardStatus)
                .theme(themeUtil.getBoardThemeSelectResponseDto(board.getBoardTheme()))
                .memos(boardStatus == BoardStatus.OPEN ? getMemoResponseDtos(board) : null)
                .build();
    }

    @Override
    @Transactional
    public void deleteBoard(BoardDeleteRequestDto requestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        if (Objects.equals(board.getPassword(), requestDto.getPassword())) {
            memoRepository.deleteByBoard(board);
            tagRepository.deleteByBoard(board);
            boardRepository.delete(board);
        }
    }

    @Override
    public WelcomeBoardResponseDto getBoardWelcomePage(String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());
        BoardTheme boardTheme = board.getBoardTheme();
        List<String> tagValues = tagRepository.findAllByBoardId(board.getId())
                .stream()
                .map(Tag::getValue)
                .toList();

        return WelcomeBoardResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .tags(tagValues)
                .memoCnt(getMemoCnt(board.getId()))
                .boardViews(board.getViews())
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .boardStatus(boardStatus)
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(themeUtil.getBackgroundValue(boardTheme))
                .boardFont(boardTheme.getFont())
                .build();
    }

    private BoardTheme getBoardTheme(BoardCreateRequestDto requestDto) {
        BoardTheme boardTheme;
        if (requestDto.getBoardThemeId() == null) {
            BoardThemeCreateRequestDto themeDto = requestDto.getTheme();
            boardTheme = saveBoardTheme(themeDto);
            saveMemoThemesWithImage(boardTheme, themeDto.getMemoImageTextColorSets());
            saveMemoThemesWithColor(boardTheme, themeDto.getMemoBackgroundTextColorSets());
        } else {
            boardTheme = boardThemeRepository.findById(requestDto.getBoardThemeId())
                    .orElseThrow(BoardThemeNotFoundException::new);
        }
        return boardTheme;
    }

    private BoardTheme saveBoardTheme(BoardThemeCreateRequestDto themeDto) {
        ThemeCategory userThemeCategory = themeUtil.getUserThemeCategory();
        String userThemeName = themeUtil.getUserThemeName();
        if (themeDto.getBoardBackgroundImage() != null) {
            String imageBase64 = themeDto.getBoardBackgroundImage();
            ImageInfo imageInfo = saveImageAndImageInfo(imageBase64, themeDto.getBoardBackgroundImageName());

            return boardThemeRepository.save(BoardTheme.builder()
                    .themeCategory(userThemeCategory)
                    .name(userThemeName)
                    .backgroundType(BackgroundType.IMAGE)
                    .backgroundImageInfo(imageInfo)
                    .font(themeDto.getBoardFont()).build());
        } else {
            return boardThemeRepository.save(BoardTheme.builder()
                    .themeCategory(userThemeCategory)
                    .name(userThemeName)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor(themeDto.getBoardBackgroundColor())
                    .font(themeDto.getBoardFont()).build());
        }
    }

    private ImageInfo saveImageAndImageInfo(String base64, String fileName) {
        ImageInfoVo imageInfoVo = fileUtil.saveImage(base64, fileName);
        return imageInfoRepository.save(ImageInfo.builder()
                .uuid(imageInfoVo.getUuid())
                .originalName(imageInfoVo.getOriginalName())
                .savedName(imageInfoVo.getSavedName())
                .extension(imageInfoVo.getExtension())
                .path(imageInfoVo.getPath())
                .build());
    }

    private static void validateBoardStatus(BoardStatus boardStatus) {
        if (boardStatus == BoardStatus.BEFORE_WRITING) {
            throw new BoardBeforeWritingException();
        } else if (boardStatus == BoardStatus.BEFORE_OPEN) {
            throw new BoardBeforeOpenException();
        } else if (boardStatus == BoardStatus.CLOSED) {
            throw new BoardClosedException();
        }
    }

    private void saveMemoThemesWithImage(BoardTheme boardTheme, List<MemoImageAndTextColorRequestDto> memoThemeDtos) {
        for (MemoImageAndTextColorRequestDto imageAndTextColor : memoThemeDtos) {
            String imageBase64 = imageAndTextColor.getMemoBackgroundImage();
            ImageInfo imageInfo = saveImageAndImageInfo(imageBase64, imageAndTextColor.getMemoBackgroundImageName());
            memoThemeRepository.save(MemoTheme.builder()
                    .boardTheme(boardTheme)
                    .backgroundType(BackgroundType.IMAGE)
                    .backgroundImageInfo(imageInfo)
                    .textColor(imageAndTextColor.getMemoTextColor())
                    .build());
        }
    }

    private void saveMemoThemesWithColor(BoardTheme boardTheme, List<MemoColorAndTextColorRequestDto> memoThemeDtos) {
        for (MemoColorAndTextColorRequestDto bgAndTextColor : memoThemeDtos) {
            memoThemeRepository.save(MemoTheme.builder()
                    .boardTheme(boardTheme)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor(bgAndTextColor.getMemoBackgroundColor())
                    .textColor(bgAndTextColor.getMemoTextColor())
                    .build());
        }
    }

    private List<MemoSelectResponseDto> getMemoResponseDtos(Board board) {
        return memoRepository.findByBoardId(board.getId())
                .stream()
                .map(memo -> MemoSelectResponseDto.builder()
                        .memoThemeId(memo.getMemoTheme().getId())
                        .memoContent(memo.getContent())
                        .build())
                .toList();
    }

    private int getMemoCnt(Long boardId) {
        return memoRepository.findByBoardId(boardId).size();
    }

    private synchronized void saveBoardWithIncreaseViews(Board board){
        board.increaseViews();
        boardRepository.saveAndFlush(board);
    }
}
