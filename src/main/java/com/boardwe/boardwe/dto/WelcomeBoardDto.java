package com.boardwe.boardwe.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.Tag;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatusType;
import com.boardwe.boardwe.type.OpenType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WelcomeBoardDto {
    public String boardName;
    public String boradDescription;
    public List<String> tags = new ArrayList<String>();
    public int memoCnt;
    public int boardViews;
    public LocalDateTime writingStartTime;
    public LocalDateTime writingEndTime;
    public LocalDateTime openStartTime;
    public LocalDateTime openEndTime;
    public OpenType openType;
    public String boardStatus;
    public BackgroundType boardBackgroundType;
    public String boardBackground;
    public String boardFont;

    public void setBoardInfo(Board board){
        this.boardName = board.getName();
        this.boradDescription = board.getDescription();
        this.boardViews = board.getViews();
        this.writingStartTime = board.getWritingStartTime();
        this.writingEndTime = board.getWritingEndTime();
        this.openStartTime = board.getOpenStartTime();
        this.openEndTime = board.getOpenEndTime();
        this.openType = board.getOpenType();
        calculateBoardState();
    }

    public void setTagListValue(List<Tag> tags){
        for(Tag tag: tags){
            this.tags.add(tag.getValue());
        }
    }

    public void setBoardThemeInfo(BoardTheme boardTheme){
        this.boardBackgroundType = boardTheme.getBackgroundType();
        this.boardFont = boardTheme.getFont();
        if(boardBackgroundType == boardBackgroundType.COLOR){
            this.boardBackground = boardTheme.getBackgroundColor();
        } else{
            this.boardBackground = boardTheme.getBackgroundImageInfo().getPath();
        }
    }

    public void calculateBoardState(){
        LocalDateTime currenDateTime = LocalDateTime.now();
        if(currenDateTime.isBefore(this.writingStartTime)){
            this.boardStatus = BoardStatusType.BEFORE_WRITING.toString();
        }
        else if(currenDateTime.isBefore(this.writingEndTime)){
            this.boardStatus = BoardStatusType.WRITING.toString();
        }
        else if(currenDateTime.isBefore(this.openStartTime)){
            this.boardStatus = BoardStatusType.BEFORE_OPEN.toString();
        }
        else if(currenDateTime.isBefore(this.openEndTime)){
            this.boardStatus = BoardStatusType.OPEN.toString();
        }
        else{
            this.boardStatus = BoardStatusType.CLOSED.toString();
        }
    }
}
