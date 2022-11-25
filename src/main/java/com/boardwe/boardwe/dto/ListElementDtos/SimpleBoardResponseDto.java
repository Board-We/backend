package com.boardwe.boardwe.dto.ListElementDtos;

import java.util.ArrayList;
import java.util.List;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.Tag;
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
public class SimpleBoardResponseDto {
    public String boardLink;
    public String boardName;
    public List<String> boardTags = new ArrayList<String>();
    public int boardViews;
    public ThemeResponseDto theme = new ThemeResponseDto();

    public void setBoardInfo(Board board){
        this.boardLink = "/board/" + board.getCode() + "/welcome";
        this.boardName = board.getName();
        this.boardViews = board.getViews();
    }

    public void setBoardTagListValue(List<Tag> tags){
        for(Tag tag:tags){
            this.boardTags.add(tag.getValue());
        }
    }

    public void setThemeObject(BoardTheme boardTheme, List<MemoTheme> memoThemes){
        this.theme.setBoardThemeInfo(boardTheme);
        this.theme.setMemoThemes(memoThemes);
    }
}
