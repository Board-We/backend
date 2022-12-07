package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.req.MemoDeleteRequestDto;

public interface MemoDeleteService {
    void deleteMemo(MemoDeleteRequestDto memoDeleteRequestDto, String boardCode);
}