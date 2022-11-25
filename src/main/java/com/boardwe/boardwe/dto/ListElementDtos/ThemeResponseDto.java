package com.boardwe.boardwe.dto.ListElementDtos;

import java.util.ArrayList;
import java.util.List;

import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Memo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.type.BackgroundType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ThemeResponseDto {
    public BackgroundType boardBackgroundType;
    public String boardBackground;
    public String boardFont;
    List<MemoThemeResponseDto> memoThemes = new ArrayList<MemoThemeResponseDto>();

    public void setBoardThemeInfo(BoardTheme boardTheme){
        this.boardBackgroundType = boardTheme.getBackgroundType();
        this.boardFont = boardTheme.getFont();
        if(boardBackgroundType == BackgroundType.COLOR){
            this.boardBackground = boardTheme.getBackgroundColor();
        } else{
            this.boardBackground = boardTheme.getBackgroundImageInfo().getPath();
        }
    }

    public void setMemoThemes(List<MemoTheme> memoThemes){
        for(MemoTheme memoTheme:memoThemes){
            MemoThemeResponseDto memoThemeResponseDto = new MemoThemeResponseDto();
            memoThemeResponseDto.setMemoThemeInfo(memoTheme);
            this.memoThemes.add(memoThemeResponseDto);
        }
    }
}
