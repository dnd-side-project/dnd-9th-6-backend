package dnd.project.global.common;

import lombok.Getter;

@Getter
public enum Result {

    OK(0, "성공"),
    FAIL(-1, "실패"),
    // User
    NOT_FOUND_USER(-1000, "존재하지 않는 사용자"),
    AT_LEAST_ONE_INTEREST_REQUIRED(-1001, "하나 이상의 관심분야를 선택해야 합니다.");

    private final int code;
    private final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
