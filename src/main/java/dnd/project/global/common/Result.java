package dnd.project.global.common;

import lombok.Getter;

@Getter
public enum Result {

    OK(0, "성공"),
    FAIL(-1, "실패"),

    // User
    NOT_FOUND_USER(-1000, "존재하지 않는 사용자"),
    AT_LEAST_ONE_INTEREST_REQUIRED(-1001, "하나 이상의 관심분야를 선택해야 합니다."),
    NOT_MATCHED_REFRESH_TOKEN(-1002, "잘못된 RefreshToken 입니다."),

    // Lecture
    NOT_FOUND_LECTURE(-2000, "존재하지 않는 강의"),
    NOT_FOUND_MAIN_AND_SUB_CATEGORY(-2001, "존재하지 않는 카테고리"),
    NOT_FOUND_MAIN_CATEGORY(-2002, "존재하지 않는 메인 카테고리"),
    NOT_FOUND_SUB_CATEGORY(-2003, "존재하지 않는 서브 카테고리"),

    // Review
    ALREADY_CREATED_REVIEW(-3000, "이미 해당 강의의 후기를 작성하였습니다."),
    NOT_FOUND_REVIEW(-3001, "존재하지 않는 리뷰"),
    NOT_MATCHED_USER(-3002, "후기를 작성한 사람이 아닙니다."),
    NOT_MY_REVIEW_LIKE(-3003, "내가 작성한 후기에는 좋아요를 남길 수 없습니다."),
    NOT_CREATED_REVIEW(-3004, "아직 후기를 한번도 작성하지 않았습니다."),

    ALREADY_CREATED_BOOKMARK(-4000, "이미 강의를 찜했습니다.");

    private final int code;
    private final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
