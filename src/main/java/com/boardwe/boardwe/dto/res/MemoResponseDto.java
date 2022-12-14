package com.boardwe.boardwe.dto.res;


import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemoResponseDto {
    private List<MemoSelectResponseDto> memos;
}
