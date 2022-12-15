package com.boardwe.boardwe.dto.req.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoThemesCreateRequestDto {
    private List<String> backgrounds;
    private List<String> textColors;
}
