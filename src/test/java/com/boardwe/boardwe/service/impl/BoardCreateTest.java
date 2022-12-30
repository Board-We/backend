package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoThemesCreateRequestDto;
import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.entity.ThemeCategory;
import com.boardwe.boardwe.exception.custom.entity.BoardThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.other.InvalidDateValueException;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardService;
import com.boardwe.boardwe.type.OpenType;
import com.boardwe.boardwe.util.FileUtil;
import com.boardwe.boardwe.util.ThemeUtil;
import com.boardwe.boardwe.util.impl.BoardInfoUtilImpl;
import com.boardwe.boardwe.vo.ImageInfoVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.MapKeyColumn;
import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BoardCreateTest {

    @Mock
    Board board;

    @Mock
    BoardThemeRepository boardThemeRepository;

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardCreateRequestDto boardCreateRequestDto;

    @Mock
    TagRepository tagRepository;

    @Mock
    ThemeUtil themeUtil;
    @Mock
    ThemeCategory themeCategory;
    @Mock
    FileUtil fileUtil;
    @Mock
    ImageInfoRepository imageInfoRepository;

    @Mock
    BoardInfoUtilImpl boardInfoUtil;
    @Mock
    MemoThemeRepository memoThemeRepository;
    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    @DisplayName("보드 테마 ID를 주었을때 보드 생성 테스트")
    void createBoardWithBoardThemeId(){
        String name = "test";
        String description = "test";
        List<String> tags = new ArrayList<>();
        tags.add("크리스마스");
        tags.add("신년");
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(3);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);
        String password = "1234";
        OpenType openType = OpenType.PUBLIC;
        Long boardThemeId = 4L;

        BoardCreateRequestDto newBoard = BoardCreateRequestDto.builder()
                .boardName(name)
                .boardDescription(description)
                .tags(tags)
                .writingStartTime(writingStartTime)
                .writingEndTime(writingEndTime)
                .openStartTime(openStartTime)
                .openEndTime(openEndTime)
                .password(password)
                .openType(openType)
                .boardThemeId(boardThemeId)
                .build();

        BoardTheme boardTheme = mock(BoardTheme.class);
        Board board = mock(Board.class);
        when(boardThemeRepository.findById(4L)).thenReturn(Optional.of(boardTheme));
        when(boardRepository.save(any())).thenReturn(board);
        when(board.getCode()).thenReturn("test");
        when(boardInfoUtil.getBoardLink("test")).thenReturn("/board/test");

        String boardLink = boardService.createBoard(newBoard).getBoardLink();

        Assertions.assertNotNull(boardLink);
    }

    @Test
    @DisplayName("보드 이미지를 주었을때 보드 생성 테스트")
    void createBoardWithBoardImage(){
        String name = "test";
        String description = "test";
        List<String> tags = new ArrayList<>();
        tags.add("크리스마스");
        tags.add("신년");
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(3);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);
        String password = "1234";
        OpenType openType = OpenType.PUBLIC;

        String imageBase64 = makeImageToBase64();

        String boardFont = "Batang";

        List<String> backgrounds = new ArrayList<>();
        backgrounds.add("#efefef");
        backgrounds.add(imageBase64);

        List<String> textColors = new ArrayList<>();
        textColors.add("#000000");
        textColors.add("#FFFFFF");


        MemoThemesCreateRequestDto memoTheme = MemoThemesCreateRequestDto.builder()
                .backgrounds(backgrounds)
                .textColors(textColors)
                .build();

        BoardCreateRequestDto newBoard = BoardCreateRequestDto.builder()
                .boardName(name)
                .boardDescription(description)
                .tags(tags)
                .writingStartTime(writingStartTime)
                .writingEndTime(writingEndTime)
                .openStartTime(openStartTime)
                .openEndTime(openEndTime)
                .password(password)
                .openType(openType)
                .boardBackground(imageBase64)
                .boardFont(boardFont)
                .memoThemes(memoTheme)
                .build();

        BoardTheme boardTheme = mock(BoardTheme.class);
        Board board = mock(Board.class);
        ImageInfoVo imageInfoVo = mock(ImageInfoVo.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        ThemeCategory themeCategory = mock(ThemeCategory.class);


        when(themeUtil.getUserThemeCategory()).thenReturn(themeCategory);
        when(themeUtil.getUserThemeName()).thenReturn("TEMP");
        when(fileUtil.saveImage(imageBase64,"imageTempName.jpg")).thenReturn(imageInfoVo);
        when(imageInfoRepository.save(any())).thenReturn(imageInfo);
        when(boardThemeRepository.save(any())).thenReturn(boardTheme);
        when(boardRepository.save(any())).thenReturn(board);
        when(board.getCode()).thenReturn("test");
        when(boardInfoUtil.getBoardLink("test")).thenReturn("/board/test");

        String boardLink = boardService.createBoard(newBoard).getBoardLink();

        Assertions.assertNotNull(boardLink);
    }

    private static String makeImageToBase64() {
        String imageBase64;
        File file = new File("/files/imageBase64Test.txt");
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            imageBase64 = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageBase64;
    }

    @Test
    @DisplayName("보드 컬러를 주었을때 보드 생성 테스트")
    void createBoardWithBoardColor(){
        String name = "test";
        String description = "test";
        List<String> tags = new ArrayList<>();
        tags.add("크리스마스");
        tags.add("신년");
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(3);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);
        String password = "1234";
        OpenType openType = OpenType.PUBLIC;

        String imageBase64 = makeImageToBase64();

        String boardFont = "Batang";

        List<String> backgrounds = new ArrayList<>();
        backgrounds.add("#efefef");
        backgrounds.add(imageBase64);

        List<String> textColors = new ArrayList<>();
        textColors.add("#000000");
        textColors.add("#FFFFFF");


        MemoThemesCreateRequestDto memoTheme = MemoThemesCreateRequestDto.builder()
                .backgrounds(backgrounds)
                .textColors(textColors)
                .build();

        BoardCreateRequestDto newBoard = BoardCreateRequestDto.builder()
                .boardName(name)
                .boardDescription(description)
                .tags(tags)
                .writingStartTime(writingStartTime)
                .writingEndTime(writingEndTime)
                .openStartTime(openStartTime)
                .openEndTime(openEndTime)
                .password(password)
                .openType(openType)
                .boardBackground("#FFFFFF")
                .boardFont(boardFont)
                .memoThemes(memoTheme)
                .build();

        BoardTheme boardTheme = mock(BoardTheme.class);
        Board board = mock(Board.class);
        ImageInfoVo imageInfoVo = mock(ImageInfoVo.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        ThemeCategory themeCategory = mock(ThemeCategory.class);

        when(themeUtil.getUserThemeCategory()).thenReturn(themeCategory);
        when(themeUtil.getUserThemeName()).thenReturn("TEMP");
        when(fileUtil.saveImage(imageBase64,"imageTempName.jpg")).thenReturn(imageInfoVo);
        when(imageInfoRepository.save(any())).thenReturn(imageInfo);
        when(boardThemeRepository.save(any())).thenReturn(boardTheme);
        when(boardRepository.save(any())).thenReturn(board);
        when(board.getCode()).thenReturn("test");
        when(boardInfoUtil.getBoardLink("test")).thenReturn("/board/test");

        String boardLink = boardService.createBoard(newBoard).getBoardLink();

        Assertions.assertNotNull(boardLink);
    }
    @Test
    @DisplayName("잘못된 보드 테마 ID를 주었을때 보드 생성 에러 테스트")
    void createBoardWithWrongBoardThemeId(){
        String name = "test";
        String description = "test";
        List<String> tags = new ArrayList<>();
        tags.add("크리스마스");
        tags.add("신년");
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(2);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(3);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);
        String password = "1234";
        OpenType openType = OpenType.PUBLIC;
        Long boardThemeId = 1L;

        BoardCreateRequestDto newBoard = BoardCreateRequestDto.builder()
                .boardName(name)
                .boardDescription(description)
                .tags(tags)
                .writingStartTime(writingStartTime)
                .writingEndTime(writingEndTime)
                .openStartTime(openStartTime)
                .openEndTime(openEndTime)
                .password(password)
                .openType(openType)
                .boardThemeId(boardThemeId)
                .build();

        Assertions.assertThrows(BoardThemeNotFoundException.class,()-> boardService.createBoard(newBoard).getBoardLink());

    }
    @Test
    @DisplayName("보드 생성 시간을 잘못 주었을 때 보드 생성 에러 테스트")
    void createBoardWithTimeError(){
        String name = "test";
        String description = "test";
        List<String> tags = new ArrayList<>();
        tags.add("크리스마스");
        tags.add("신년");
        LocalDateTime writingStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime writingEndTime = LocalDateTime.now().plusDays(5);
        LocalDateTime openStartTime = LocalDateTime.now().plusDays(3);
        LocalDateTime openEndTime = LocalDateTime.now().plusDays(4);
        String password = "1234";
        OpenType openType = OpenType.PUBLIC;
        Long boardThemeId = 4L;

        BoardCreateRequestDto newBoard = BoardCreateRequestDto.builder()
                .boardName(name)
                .boardDescription(description)
                .tags(tags)
                .writingStartTime(writingStartTime)
                .writingEndTime(writingEndTime)
                .openStartTime(openStartTime)
                .openEndTime(openEndTime)
                .password(password)
                .openType(openType)
                .boardThemeId(boardThemeId)
                .build();

        BoardTheme boardTheme = mock(BoardTheme.class);
        Board board = mock(Board.class);
        when(boardThemeRepository.findById(4L)).thenReturn(Optional.of(boardTheme));



        Assertions.assertThrows(InvalidDateValueException.class,()->{
            boardService.createBoard(newBoard).getBoardLink();
        });
    }


}
