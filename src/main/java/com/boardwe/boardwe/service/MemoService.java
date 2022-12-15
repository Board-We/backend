package com.boardwe.boardwe.service;

import java.util.List;

import com.boardwe.boardwe.dto.req.MemoCreateRequestDto;
import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;
import com.boardwe.boardwe.dto.res.MemoSearchResponseDto;
import com.boardwe.boardwe.dto.res.MemoSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoCreateResponseDto;

public interface MemoService {
    MemoCreateResponseDto createMemo(MemoCreateRequestDto memoCreateRequestDto, String boardCode);
    void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode);
    MemoSearchResponseDto searchMemo(String boardCode, String query);
    List<MemoSelectResponseDto> getMemo(String boardCode, String password);

}
