package com.boardwe.boardwe.exception;

public enum ErrorCode {
    // -------- 4xx --------
    REQUEST_ERROR(400,"잘못된 요청입니다."),

    INVALID_ACCESS(400, "다시 로그인 후 이용해주세요."),
    // Invalid Input
    INVALID_INPUT_VALUE(400, "입력값이 유효하지 않습니다."),
    INVALID_DATE_VALUE(400, "날짜 순서가 유효하지 않습니다."),
    INVALID_PASSWORD(400,"틀린 비밀번호 입니다."),
    INVALID_MEMO_THEME_LIST(400, "메모리 리스트가 올바르지 않습니다."),

    // Entity Not Found
    ENTITY_NOT_FOUND(400, "해당 리소스가 존재하지 않습니다."),
    BOARD_NOT_FOUND(400, "해당 보드가 존재하지 않습니다."),
    IMAGE_INFO_NOT_FOUND(400, "해당 이미지 정보가 존재하지 않습니다."),

    // About Board Status
    BOARD_BEFORE_WRITING(400, "롤링페이퍼 작성기간 이전입니다."),
    BOARD_WRITING(400,"롤링페이퍼 작성기간입니다."),
    BOARD_BEFORE_OPEN(400, "롤링페이퍼의 작성기간이 마감되었습니다."),
    BOARD_CLOSED(400, "롤링페이퍼의 공개기간이 만료되었습니다."),
    BOARD_THEME_NOT_FOUND(400,"해당 보드 테마가 존재하지 않습니다."),
    BOARD_CANNOT_WRITE(400, "롤링페이퍼 작성기간이 아닙니다."),
    BOARD_NOT_OPENED(400, "롤링페이퍼 공개기간이 아닙니다."),

    // File
    IMAGE_NOT_FOUND(400, "이미지가 존재하지 않습니다."),
    UNABLE_TO_CREATE_DIRECTORY(400, "파일 디렉토리를 생성할 수 없습니다."),
    CANNOT_STORE_FILE(400,"파일을 저장할 수 없습니다."),


    // -------- 5xx --------
    MEMO_NOT_FOUND(400,"메모를 찾을 수 없습니다."),

    MEMO_THEME_NOT_FOUND(400,"메모 테마를 찾을 수 없습니다."),

    MEMO_WITH_INVALID_BOARD(400,"보드에 속하지 않는 메모입니다."),
    //5xx
    INTERNAL_SERVER_ERROR(500,"서버에 문제가 발생했습니다.");

    private final int status;
    private final String message;
    ErrorCode(final int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
