package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.req.MemoAddRequestDto;
import com.boardwe.boardwe.dto.res.MemoAddResponseDto;

public interface MemoAddService {
    MemoAddResponseDto addMemo(MemoAddRequestDto memoAddRequestDto, String boardCode);
}
