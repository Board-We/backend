package com.boardwe.boardwe.dto.ListElementDtos;

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
public class MemoThemeResponseDto {
    public BackgroundType memoBackgroundType;
    public String memoBackground;
    public String memoTextColor;

    public void setMemoThemeInfo(MemoTheme memoTheme){
        this.memoBackgroundType = memoTheme.getBackgroundType();
        this.memoTextColor = memoTheme.getTextColor();
        if(this.memoBackgroundType == BackgroundType.COLOR){
            this.memoBackground = memoTheme.getBackgroundColor();
        }
        else{
            this.memoBackground = memoTheme.getBackgroundImageInfo().getPath();
        }
    }
}
