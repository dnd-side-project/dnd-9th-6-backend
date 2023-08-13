package dnd.project.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewTag {

    TAG001("강사에 대해", "빠른 답변"),
    TAG002("강사에 대해", "듣기 좋은 목소리"),
    TAG003("강사에 대해", "뛰어난 강의력"),
    TAG004("강사에 대해", "매우 적극적"),

    TAG005("강의내용에 대해", "커리큘럼과 똑같아요"),
    TAG006("강의내용에 대해", "구성이 알차요"),
    TAG007("강의내용에 대해", "내용이 자세해요"),
    TAG008("강의내용에 대해", "이해가 잘돼요"),

    TAG009("강의후, 느끼는 변화", "도움이 많이 됐어요"),
    TAG0010("강의후, 느끼는 변화", "보통이에요"),
    TAG0011("강의후, 느끼는 변화", "도움이 안되었어요");

    private final String type;
    private final String name;
}
