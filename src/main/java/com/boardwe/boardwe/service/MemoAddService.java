package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.MemoAddRequestDto;
import com.boardwe.boardwe.dto.MemoAddResponseDto;

import java.time.LocalDateTime;

public interface MemoAddService {
    MemoAddResponseDto addMemo(MemoAddRequestDto memoAddRequestDto, String boardCode);
}
