package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoThemesCreateRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.res.BoardReadResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.entity.BoardThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.InvalidMemoThemeListException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.BoardInfoUtil;
import com.boardwe.boardwe.util.FileUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import com.boardwe.boardwe.vo.ImageInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
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
        log.info("[BoardServiceImpl] Create board (board name: {}).", requestDto.getBoardName());
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

        String boardLink = boardInfoUtil.getBoardLink(board.getCode());
        log.info("[BoardServiceImpl] Board is created (board link: {}).", boardLink);
        return BoardCreateResponseDto.builder()
                .boardLink(boardLink)
                .build();
    }

    @Override
    @Transactional
    public BoardReadResponseDto readBoard(String boardCode) {
        log.info("[BoardServiceImpl] Read board info (board code: {}).", boardCode);
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        BoardTheme boardTheme = board.getBoardTheme();
        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());

        List<String> tagValues = tagRepository.findAllByBoardId(board.getId())
                .stream()
                .map(Tag::getValue)
                .toList();

        OpenType boardOpenType = board.getOpenType();

        saveBoardWithIncreaseViews(board);

        return BoardReadResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .boardTags(tagValues)
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .openType(boardOpenType)
                .boardStatus(boardStatus)
                .boardFont(boardTheme.getFont())
                .boardViews(board.getViews())
                .boardBackgroundType(boardOpenType == OpenType.PUBLIC ? boardTheme.getBackgroundType() : null)
                .boardBackground(boardOpenType == OpenType.PUBLIC ? themeUtil.getBackgroundValue(boardTheme) : null)
                .build();
    }

    @Override
    @Transactional
    public void deleteBoard(String boardCode) {
        log.info("[BoardServiceImpl] Delete board (board code: {}).", boardCode);
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        memoRepository.deleteByBoard(board);
        tagRepository.deleteByBoard(board);
        boardRepository.delete(board);
    }

    private BoardTheme getBoardTheme(BoardCreateRequestDto createDto) {
        BoardTheme boardTheme;
        if (createDto.getBoardThemeId() == null) {
            log.info("[BoardServiceImpl] There is no board theme id. Create new board theme.");
            boardTheme = saveBoardTheme(createDto.getBoardBackground(), createDto.getBoardFont());

            MemoThemesCreateRequestDto memoThemes = createDto.getMemoThemes();
            int memoThemeLen = memoThemes.getBackgrounds().size();
            if (memoThemes.getTextColors().size() != memoThemeLen) {
                log.error("[BoardServiceImpl] The num of memo text colors and memo background is not equal. Cannot create theme.");
                throw new InvalidMemoThemeListException();
            }

            if (memoThemeLen == 0) {
                setBasicMemoTheme(boardTheme);
            }

            for (int idx = 0; idx < memoThemeLen; idx++) {
                String bgValue = memoThemes.getBackgrounds().get(idx);
                String textColor = memoThemes.getTextColors().get(idx);
                if (isColorCodeString(bgValue)) saveMemoThemeWithColor(boardTheme, bgValue, textColor);
                else saveMemoThemeWithImage(boardTheme, bgValue, textColor);
            }
        } else {
            boardTheme = boardThemeRepository.findById(createDto.getBoardThemeId())
                    .orElseThrow(BoardThemeNotFoundException::new);
            log.info("[BoardServiceImpl] Get board theme: {}", boardTheme.getName());
        }
        return boardTheme;
    }

    private BoardTheme saveBoardTheme(String boardBackgroundValue, String boardFont) {
        ThemeCategory userThemeCategory = themeUtil.getUserThemeCategory();
        String userThemeName = themeUtil.getUserThemeName();
        if (isColorCodeString(boardBackgroundValue)) {
            return boardThemeRepository.save(BoardTheme.builder()
                    .themeCategory(userThemeCategory)
                    .name(userThemeName)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor(boardBackgroundValue)
                    .font(boardFont).build());
        } else {
            ImageInfo imageInfo = saveImageAndImageInfo(boardBackgroundValue);

            return boardThemeRepository.save(BoardTheme.builder()
                    .themeCategory(userThemeCategory)
                    .name(userThemeName)
                    .backgroundType(BackgroundType.IMAGE)
                    .backgroundImageInfo(imageInfo)
                    .font(boardFont).build());
        }
    }

    private ImageInfo saveImageAndImageInfo(String base64) {
        ImageInfoVo imageInfoVo = fileUtil.saveImage(base64, "imageTempName.jpg");
        return imageInfoRepository.save(ImageInfo.builder()
                .uuid(imageInfoVo.getUuid())
                .originalName(imageInfoVo.getOriginalName())
                .savedName(imageInfoVo.getSavedName())
                .extension(imageInfoVo.getExtension())
                .path(imageInfoVo.getPath())
                .build());
    }

    private void setBasicMemoTheme(BoardTheme boardTheme) {
        log.info("[BoardServiceImpl] There is no memo theme data. Set memo theme with basic color.");
        saveMemoThemeWithColor(boardTheme, "#FFFFE0", "#000000");
    }

    private void saveMemoThemeWithImage(BoardTheme boardTheme, String bgBase64, String textColor) {
        ImageInfo imageInfo = saveImageAndImageInfo(bgBase64);
        memoThemeRepository.save(MemoTheme.builder()
                .boardTheme(boardTheme)
                .backgroundType(BackgroundType.IMAGE)
                .backgroundImageInfo(imageInfo)
                .textColor(textColor)
                .build());
    }

    private void saveMemoThemeWithColor(BoardTheme boardTheme, String bgColor, String textColor) {
        memoThemeRepository.save(MemoTheme.builder()
                .boardTheme(boardTheme)
                .backgroundType(BackgroundType.COLOR)
                .backgroundColor(bgColor)
                .textColor(textColor)
                .build());
    }

    private int getMemoCnt(Long boardId) {
        return memoRepository.findByBoardId(boardId).size();
    }

    private synchronized void saveBoardWithIncreaseViews(Board board) {
        log.info("[BoardServiceImpl] Increase board view.");
        board.increaseViews();
        boardRepository.saveAndFlush(board);
    }

    private Boolean isColorCodeString(String backgroundString) {
        return backgroundString.startsWith("#") && backgroundString.length() < 10;
    }
}
