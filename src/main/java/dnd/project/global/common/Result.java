package dnd.project.global.common;

import lombok.Getter;

@Getter
public enum Result {

    OK(0, "성공"),
    FAIL(-1, "실패"),
    // User
    NOT_FOUND_USER(-1000, "존재하지 않는 사용자"),
    AT_LEAST_ONE_INTEREST_REQUIRED(-1001, "하나 이상의 관심분야를 선택해야 합니다."),

    // Lecture
    NOT_FOUND_LECTURE(-2000, "존재하지 않는 강의"),

    // Review
    ALREADY_CREATED_REVIEW(-3000, "이미 해당 강의의 후기를 작성하였습니다."),
    NOT_FOUND_REVIEW(-3001, "존재하지 않는 리뷰"),
    NOT_MATCHED_USER(-3002, "후기를 작성한 사람이 아닙니다." );

    private final int code;
    private final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
