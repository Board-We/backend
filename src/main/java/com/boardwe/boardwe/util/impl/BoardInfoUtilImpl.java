package com.boardwe.boardwe.util.impl;

import com.boardwe.boardwe.util.BoardInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardInfoUtilImpl implements BoardInfoUtil {

    @Override
    public String getBoardLink(String boardCode) {
        return String.format("/board/%s", boardCode);
    }
}
