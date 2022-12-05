package com.boardwe.boardwe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoDeleteRequestDto {
    private List<Long> memoIds;
}
