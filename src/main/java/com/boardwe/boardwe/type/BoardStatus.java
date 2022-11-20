package com.boardwe.boardwe.type;

import java.time.LocalDateTime;

public enum BoardStatus {
    BEFORE_WRITING, WRITING, BEFORE_OPEN, OPEN, CLOSED;

    public static BoardStatus calculateBoardStatus(
            LocalDateTime writingStartTime, LocalDateTime writingEndTime,
            LocalDateTime openStartTime, LocalDateTime openEndTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isBefore(writingStartTime)) {
            return BEFORE_WRITING;
        } else if (currentTime.isBefore(writingEndTime)) {
            return WRITING;
        } else if (currentTime.isBefore(openStartTime)) {
            return BEFORE_OPEN;
        } else if (currentTime.isBefore(openEndTime)) {
            return OPEN;
        } else {
            return CLOSED;
        }
    }
}
