package dnd.project.domain.lecture.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LectureCategory {

    CATEGORY001("게임", "게임/e스포츠", 1, 1, 4),
    CATEGORY002("게임", "게임개발", 2, 1, 5),
    CATEGORY003("게임", "게임제작", 3, 1, 6),
    CATEGORY004("금융/투자", "금융투자실무", 4, 2, 9),
    CATEGORY005("금융/투자", "부동산", 5, 2, 26),
    CATEGORY006("금융/투자", "재무/회계/세무", 6, 2, 51),
    CATEGORY007("금융/투자", "재테크/주식", 7, 2, 52),
    CATEGORY008("데이터사이언스", "기타", 8, 3, 10),
    CATEGORY009("데이터사이언스", "데이터분석", 9, 3, 13),
    CATEGORY010("데이터사이언스", "데이터시각화", 10, 3, 14),
    CATEGORY011("데이터사이언스", "데이터엔지니어링", 11, 3, 15),
    CATEGORY012("데이터사이언스", "인공지능", 12, 3, 48),
    CATEGORY013("드로잉", "웹툰/웹소설", 13, 4, 46),
    CATEGORY014("드로잉", "취미드로잉", 14, 4, 56),
    CATEGORY015("드로잉", "캐릭터일러스트", 15, 4, 57),
    CATEGORY016("드로잉", "컨셉 아트", 16, 4, 58),
    CATEGORY017("디자인", "2D/그래픽/브랜딩", 17, 5, 1),
    CATEGORY018("디자인", "3D/건축", 18, 5, 2),
    CATEGORY019("디자인", "제품디자인", 19, 5, 53),
    CATEGORY020("디자인", "UX/UI", 20, 5, 64),
    CATEGORY021("라이프 스타일", "기타", 21, 6, 10),
    CATEGORY022("라이프 스타일", "뷰티", 22, 6, 27),
    CATEGORY023("라이프 스타일", "심리", 23, 6, 35),
    CATEGORY024("라이프 스타일", "운동", 24, 6, 44),
    CATEGORY025("라이프 스타일", "음악", 25, 6, 47),
    CATEGORY026("영상/3D/애니메이션", "3D/CG", 26, 7, 3),
    CATEGORY027("영상/3D/애니메이션", "모션그래픽", 27, 7, 22),
    CATEGORY028("영상/3D/애니메이션", "블렌더", 28, 7, 28),
    CATEGORY029("영상/3D/애니메이션", "애니메이션", 29, 7, 37),
    CATEGORY030("영상/3D/애니메이션", "영상/사진", 30, 7, 40),
    CATEGORY031("영상/3D/애니메이션", "VR/AR", 31, 7, 65),
    CATEGORY032("요리", "베이킹/디저트", 32, 8, 24),
    CATEGORY033("요리", "요리/음료", 33, 8, 43),
    CATEGORY034("커리어", "기타", 34, 9, 10),
    CATEGORY035("커리어", "마케팅", 35, 9, 17),
    CATEGORY036("커리어", "마케팅/기타", 36, 9, 18),
    CATEGORY037("커리어", "비즈니스", 37, 9, 30),
    CATEGORY038("커리어", "비즈니스/기획", 38, 9, 31),
    CATEGORY039("커리어", "업무생산성", 39, 9, 39),
    CATEGORY040("커리어", "자격증", 40, 9, 50),
    CATEGORY041("커리어", "창업/부업", 41, 9, 54),
    CATEGORY042("커리어", "창업/부업/재테크/쇼핑몰", 42, 9, 55),
    CATEGORY043("크리에이티브", "공예", 43, 10, 7),
    CATEGORY044("크리에이티브", "기타", 44, 10, 10),
    CATEGORY045("크리에이티브", "메타버스", 45, 10, 19),
    CATEGORY046("크리에이티브", "소설/글쓰기", 46, 10, 32),
    CATEGORY047("크리에이티브", "음악", 47, 10, 47),
    CATEGORY048("크리에이티브", "패션/사진", 48, 10, 62),
    CATEGORY049("크리에이티브", "헤어/뷰티", 49, 10, 63),
    CATEGORY050("프로그래밍", "교양", 50, 11, 8),
    CATEGORY051("프로그래밍", "기타", 51, 11, 10),
    CATEGORY052("프로그래밍", "데브옵스/인프라", 52, 11, 12),
    CATEGORY053("프로그래밍", "모바일", 53, 11, 20),
    CATEGORY054("프로그래밍", "보안/네트워크", 54, 11, 25),
    CATEGORY055("프로그래밍", "블록체인", 55, 11, 29),
    CATEGORY056("프로그래밍", "시스템", 56, 11, 34),
    CATEGORY057("프로그래밍", "앱", 57, 11, 38),
    CATEGORY058("프로그래밍", "웹", 58, 11, 45),
    CATEGORY059("프로그래밍", "컴퓨터공학", 59, 11, 59),
    CATEGORY060("프로그래밍", "클라우드", 60, 11, 61),
    CATEGORY061("하드웨어", "기타", 61, 12, 10),
    CATEGORY062("하드웨어", "로봇", 62, 12, 16),
    CATEGORY063("하드웨어", "모빌리티", 63, 12, 21),
    CATEGORY064("하드웨어", "반도체", 64, 12, 23),
    CATEGORY065("하드웨어", "임베디드", 65, 12, 49),
    CATEGORY066("하드웨어", "컴퓨터구조", 66, 12, 60),
    CATEGORY067("학문/외국어", "기타", 67, 13, 10),
    CATEGORY068("학문/외국어", "기타외국어", 68, 13, 11),
    CATEGORY069("학문/외국어", "수학", 69, 13, 33),
    CATEGORY070("학문/외국어", "아이/부모교육", 70, 13, 36),
    CATEGORY071("학문/외국어", "영어", 71, 13, 41),
    CATEGORY072("학문/외국어", "영어/기타외국어", 72, 13, 42);

    private final String mainCategoryName;
    private final String subCategoryName;
    private final Integer categoryId;
    private final Integer mainCategoryId;
    private final Integer subCategoryId;
}
