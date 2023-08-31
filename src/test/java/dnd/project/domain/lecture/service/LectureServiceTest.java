package dnd.project.domain.lecture.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.lecture.response.LectureListReadResponse;
import dnd.project.domain.lecture.response.LectureReadResponse;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.entity.ReviewTag;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dnd.project.global.common.Result.NOT_FOUND_MAIN_AND_SUB_CATEGORY;
import static dnd.project.global.common.Result.NOT_FOUND_MAIN_CATEGORY;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LectureServiceTest {

    @Autowired
    LectureService lectureService;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;

    @DisplayName("강의 검색 - 메인 카테고리, 서브 카테고리가 유효하지 않을 때")
    @Test
    void getLectures1() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when & then
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(999,
                                1,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_AND_SUB_CATEGORY);
    }

    @DisplayName("강의 검색 - 메인 카테고리만 존재하고 유효하지 않을 때")
    @Test
    void getLectures2() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when & then
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(999,
                                null,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_CATEGORY);
    }

    @DisplayName("강의 검색 - 서브 카테고리만 존재하고 유효하지 않을 때")
    @Test
    void getLectures3() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when & then
        Assertions.assertThatThrownBy(() ->
                        lectureService.getLectures(null,
                                1,
                                null,
                                0,
                                10,
                                null))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("result", NOT_FOUND_MAIN_CATEGORY);
    }

    @DisplayName("강의 검색 - 카테고리가 없을 때 전체 검색")
    @Test
    void getLectures4() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when & then
        LectureListReadResponse response = lectureService.getLectures(null,
                null,
                null,
                0,
                10,
                null);

        Assertions.assertThat(response.getLectures().size()).isEqualTo(totalLectures.size());
    }

    @DisplayName("강의 상세 조회")
    @Test
    void getLectures5() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("test1@test.com", "테스터1"),
                saveUser("test2@test.com", "테스터2"),
                saveUser("test3@test.com", "테스터3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(0),
                        totalUsers.get(0),
                        5.0,
                        ReviewTag.TAG001.getName() + "," +
                                ReviewTag.TAG002.getName() + "," +
                                ReviewTag.TAG003.getName() + "," +
                                ReviewTag.TAG004.getName() + "," +
                                ReviewTag.TAG005.getName() + "," +
                                ReviewTag.TAG0010.getName() + "," +
                                ReviewTag.TAG0011.getName(), "내용이 알찹니다!"),

                getReview(totalLectures.get(0),
                        totalUsers.get(1),
                        4.0,
                        ReviewTag.TAG001.getName() + "," +
                                ReviewTag.TAG005.getName() + "," +
                                ReviewTag.TAG009.getName(),
                        "아주 퍼펙트한 강의 이군요"),

                getReview(totalLectures.get(0),
                        totalUsers.get(2),
                        2.5,
                        ReviewTag.TAG001.getName() + "," +
                                ReviewTag.TAG006.getName() + "," +
                                ReviewTag.TAG0010.getName(),
                        "이 강의를 듣고 개념이 달라졌습니다."));

        reviewRepository.saveAll(totalReviews);

        // when
        LectureReadResponse response = lectureService.getLecture(totalLectures.get(0).getId());

        Assertions.assertThat(response.getReviewCount()).isEqualTo(3);

        List<LectureReadResponse.TagGroup> tagGroups = response.getTagGroups();
        Assertions.assertThat(tagGroups.size()).isEqualTo(3);
        Assertions.assertThat(tagGroups).extracting("name").contains("강사에 대해", "강의내용에 대해", "강의후, 느끼는 변화");

        for (LectureReadResponse.TagGroup tagGroup : tagGroups) {
            List<LectureReadResponse.TagGroup.Tag> tags = tagGroup.getTags();

            if (tagGroup.getName().equals("강사에 대해")) {
                Assertions.assertThat(tags.size()).isEqualTo(4);
            } else if (tagGroup.getName().equals("강의내용에 대해")) {
                Assertions.assertThat(tags.size()).isEqualTo(4);
            } else {
                Assertions.assertThat(tags.size()).isEqualTo(3);
            }
        }
    }

    // method

    private static Review getReview(
            Lecture lecture, Users randomUser, double score, String tags, String content
    ) {
        return Review.builder()
                .user(randomUser)
                .lecture(lecture)
                .score(score)
                .tags(tags)
                .content(content)
                .build();
    }

    private static Lecture getLecture(String randomLectureTitle, String randomMainCategory) {
        return Lecture.builder()
                .title(randomLectureTitle)
                .source("coloso")
                .url("URL")
                .price("가격")
                .name("이름")
                .mainCategory(randomMainCategory)
                .subCategory("하위 카테고리")
                .keywords("키워드1, 키워드2, 키워드3")
                .content("강의 내용")
                .imageUrl("이미지 URL")
                .build();
    }

    private static Lecture getLecture(String title,
                                      String price,
                                      String name,
                                      String mainCategory,
                                      String subCategory,
                                      String keywords,
                                      String content) {
        return Lecture.builder()
                .title(title)
                .source("출처")
                .url("URL")
                .price(price)
                .name(name)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .keywords(keywords)
                .content(content)
                .imageUrl("이미지 URL")
                .build();
    }

    private Users saveUser(String email, String nickname) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests("관심사1, 관심사2")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Users saveUser(String email, String nickname, String interests) {
        return Users.builder()
                .email(email)
                .password("password")
                .imageUrl("이미지 URL ")
                .nickName(nickname)
                .interests(interests)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
