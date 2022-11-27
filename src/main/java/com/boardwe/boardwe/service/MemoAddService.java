package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.MemoAddRequestDto;

import java.time.LocalDateTime;

public interface MemoAddService {
    LocalDateTime addMemo(MemoAddRequestDto memoAddRequestDto, String boardCode);
}
