package dnd.project.domain.lecture.repository;

import dnd.project.domain.TestQuerydslConfig;
import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.bookmark.repository.BookmarkRepository;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.response.LectureReviewListReadResponse;
import dnd.project.domain.lecture.response.LectureScopeListReadResponse;
import dnd.project.domain.review.entity.LikeReview;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.LikeReviewRepository;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.user.entity.Authority;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestQuerydslConfig.class)
@DataJpaTest
class LectureQueryRepositoryTest {

    @Autowired
    LectureQueryRepository lectureQueryRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    LikeReviewRepository likeReviewRepository;

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 없음")
    @Test
    void findLectures1() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(3);
        assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 스프링")
    @Test
    void findLectures2() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "스프링", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 김영한")
    @Test
    void findLectures3() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);
        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "김영한", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 iOS")
    @Test
    void findLectures4() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "iOS", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 백엔드")
    @Test
    void findLectures5() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "백엔드", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 앱")
    @Test
    void findLectures6() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "앱", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 카테고리 없음, 키워드 네이티브")
    @Test
    void findLectures7() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll(null, null, "네이티브", 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 키워드 없음")
    @Test
    void findLectures8() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", null, null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(3);
        assertThat(lectures.getTotalElements()).isEqualTo(5);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 앱, 키워드 없음")
    @Test
    void findLectures9() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "앱", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "앱", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "앱", null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(1);
        assertThat(lectures.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("강의 검색 - 메인 카테고리 프로그래밍, 서브 카테고리 웹, 키워드 없음")
    @Test
    void findLectures10() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "앱", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "앱", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍", "웹", null, 0, 2, null);

        // then
        assertThat(lectures.getTotalPages()).isEqualTo(2);
        assertThat(lectures.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("강의 검색 - 북마크수 오름 차순")
    @Test
    void findLectures11() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Bookmark> totalBookmarks = List.of(
                getBookMark(totalLectures.get(0), totalUsers.get(0)),
                getBookMark(totalLectures.get(0), totalUsers.get(1)),
                getBookMark(totalLectures.get(0), totalUsers.get(2)),
                getBookMark(totalLectures.get(2), totalUsers.get(1)),
                getBookMark(totalLectures.get(1), totalUsers.get(2)));
        bookmarkRepository.saveAll(totalBookmarks);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "bookmark,asc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(3).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(4).getTitle());
    }

    @DisplayName("강의 검색 - 북마크수 내림 차순")
    @Test
    void findLectures12() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Bookmark> totalBookmarks = List.of(
                getBookMark(totalLectures.get(0), totalUsers.get(0)),
                getBookMark(totalLectures.get(0), totalUsers.get(1)),
                getBookMark(totalLectures.get(0), totalUsers.get(2)),
                getBookMark(totalLectures.get(2), totalUsers.get(1)),
                getBookMark(totalLectures.get(1), totalUsers.get(2)));
        bookmarkRepository.saveAll(totalBookmarks);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "bookmark,desc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(0).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(1).getTitle());
    }

    @DisplayName("강의 검색 - 리뷰수 오름 차순")
    @Test
    void findLectures13() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "review,asc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(0).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(1).getTitle());
    }

    @DisplayName("강의 검색 - 리뷰수 내림 차순")
    @Test
    void findLectures14() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                5,
                "review,desc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(4).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(2).getTitle());
    }

    @DisplayName("강의 검색 - 가격 오름 차순")
    @Test
    void findLectures15() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "price,asc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(3).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(0).getTitle());
    }

    @DisplayName("강의 검색 - 가격 내림 차순")
    @Test
    void findLectures16() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        // when
        Page<Lecture> lectures = lectureQueryRepository.findAll("프로그래밍",
                null,
                null,
                0,
                2,
                "price,desc");

        // then
        List<Lecture> content = lectures.getContent();
        assertThat(content.get(0).getTitle()).isEqualTo(totalLectures.get(4).getTitle());
        assertThat(content.get(1).getTitle()).isEqualTo(totalLectures.get(0).getTitle());
    }

    @DisplayName("리뷰 검색 - 기본 ID 오름 차순")
    @Test
    void findReviews1() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                null);

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의1");
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 평점 오름 차순")
    @Test
    void findReviews2() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "score,asc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의3");
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 평점 내림 차순")
    @Test
    void findReviews3() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "score,desc");

        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();

        // then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의1");
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 작성일 오름 차순")
    @Test
    void findReviews4() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "createdDate,asc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의1");
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 작성일 내림 차순")
    @Test
    void findReviews5() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "createdDate,desc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의3");
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 추천 오름 차순")
    @Test
    void findReviews6() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        List<LikeReview> likeReviews = List.of(
                getLikeReview(totalReviews.get(6), totalUsers.get(0)),
                getLikeReview(totalReviews.get(6), totalUsers.get(1)),
                getLikeReview(totalReviews.get(6), totalUsers.get(2)),
                getLikeReview(totalReviews.get(5), totalUsers.get(0)),
                getLikeReview(totalReviews.get(5), totalUsers.get(1)),
                getLikeReview(totalReviews.get(5), totalUsers.get(2)),
                getLikeReview(totalReviews.get(4), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(1)),
                getLikeReview(totalReviews.get(0), totalUsers.get(2)),
                getLikeReview(totalReviews.get(2), totalUsers.get(0)));
        likeReviewRepository.saveAll(likeReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "like,asc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의1");
        assertThat(content.get(0).getLikeCount()).isEqualTo(1);
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의3");
        assertThat(content.get(1).getLikeCount()).isEqualTo(1);

    }

    @DisplayName("리뷰 검색 - 추천 내림 차순")
    @Test
    void findReviews7() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        List<LikeReview> likeReviews = List.of(
                getLikeReview(totalReviews.get(6), totalUsers.get(0)),
                getLikeReview(totalReviews.get(6), totalUsers.get(1)),
                getLikeReview(totalReviews.get(6), totalUsers.get(2)),
                getLikeReview(totalReviews.get(5), totalUsers.get(0)),
                getLikeReview(totalReviews.get(5), totalUsers.get(1)),
                getLikeReview(totalReviews.get(5), totalUsers.get(2)),
                getLikeReview(totalReviews.get(4), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(1)),
                getLikeReview(totalReviews.get(0), totalUsers.get(2)),
                getLikeReview(totalReviews.get(2), totalUsers.get(0)));
        likeReviewRepository.saveAll(likeReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(4).getId(),
                null,
                0,
                2,
                "like,desc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("iOS 개발 추천 강의2");
        assertThat(content.get(0).getLikeCount()).isEqualTo(2);
        assertThat(content.get(1).getContent()).isEqualTo("iOS 개발 추천 강의1");
        assertThat(content.get(1).getLikeCount()).isEqualTo(1);

    }

    @DisplayName("리뷰 검색 - 평점 오름 차순")
    @Test
    void findReviews8() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(2).getId(),
                null,
                0,
                2,
                "score,asc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("스프링 개발 추천 강의2");
        assertThat(content.get(1).getContent()).isEqualTo("스프링 개발 추천 강의1");
    }

    @DisplayName("리뷰 검색 - 평점 내림 차순")
    @Test
    void findReviews9() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(2).getId(),
                null,
                0,
                2,
                "score,desc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("스프링 개발 추천 강의1");
        assertThat(content.get(1).getContent()).isEqualTo("스프링 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 평점 내림 차순, 검색 키워드")
    @Test
    void findReviews10() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(2).getId(),
                "스프링 개발 추천 강의2",
                0,
                2,
                "score,desc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getContent()).isEqualTo("스프링 개발 추천 강의2");
    }

    @DisplayName("리뷰 검색 - 추천 오름 차순")
    @Test
    void findReviews11() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        List<LikeReview> likeReviews = List.of(
                getLikeReview(totalReviews.get(6), totalUsers.get(0)),
                getLikeReview(totalReviews.get(6), totalUsers.get(1)),
                getLikeReview(totalReviews.get(6), totalUsers.get(2)),
                getLikeReview(totalReviews.get(5), totalUsers.get(0)),
                getLikeReview(totalReviews.get(5), totalUsers.get(1)),
                getLikeReview(totalReviews.get(5), totalUsers.get(2)),
                getLikeReview(totalReviews.get(4), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(1)),
                getLikeReview(totalReviews.get(0), totalUsers.get(2)),
                getLikeReview(totalReviews.get(2), totalUsers.get(0)));
        likeReviewRepository.saveAll(likeReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(2).getId(),
                null,
                0,
                2,
                "like,asc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("스프링 개발 추천 강의1");
        assertThat(content.get(0).getLikeCount()).isEqualTo(0);
        assertThat(content.get(1).getContent()).isEqualTo("스프링 개발 추천 강의2");
        assertThat(content.get(1).getLikeCount()).isEqualTo(1);
    }

    @DisplayName("리뷰 검색 - 추천 내림 차순")
    @Test
    void findReviews12() {
        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        List<LikeReview> likeReviews = List.of(
                getLikeReview(totalReviews.get(6), totalUsers.get(0)),
                getLikeReview(totalReviews.get(6), totalUsers.get(1)),
                getLikeReview(totalReviews.get(6), totalUsers.get(2)),
                getLikeReview(totalReviews.get(5), totalUsers.get(0)),
                getLikeReview(totalReviews.get(5), totalUsers.get(1)),
                getLikeReview(totalReviews.get(5), totalUsers.get(2)),
                getLikeReview(totalReviews.get(4), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(0)),
                getLikeReview(totalReviews.get(1), totalUsers.get(1)),
                getLikeReview(totalReviews.get(0), totalUsers.get(2)),
                getLikeReview(totalReviews.get(2), totalUsers.get(0)));
        likeReviewRepository.saveAll(likeReviews);

        // when
        Page<LectureReviewListReadResponse.ReviewInfo> reviews = lectureQueryRepository.findAllReviewsById(totalLectures.get(2).getId(),
                null,
                0,
                2,
                "like,desc");

        // then
        List<LectureReviewListReadResponse.ReviewInfo> content = reviews.getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getContent()).isEqualTo("스프링 개발 추천 강의2");
        assertThat(content.get(0).getLikeCount()).isEqualTo(1);
        assertThat(content.get(1).getContent()).isEqualTo("스프링 개발 추천 강의1");
        assertThat(content.get(1).getLikeCount()).isEqualTo(0);
    }

    @DisplayName("강의 리뷰수 조회")
    @Test
    void findLectureReviewCount() {

        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Review> totalReviews = List.of(
                getReview(totalLectures.get(4), totalUsers.get(0), 5.0, "좋아요1", "iOS 개발 추천 강의1"),
                getReview(totalLectures.get(4), totalUsers.get(1), 4.5, "좋아요2", "iOS 개발 추천 강의2"),
                getReview(totalLectures.get(4), totalUsers.get(2), 4.0, "좋아요3", "iOS 개발 추천 강의3"),
                getReview(totalLectures.get(2), totalUsers.get(2), 3.5, "좋아요4", "스프링 개발 추천 강의1"),
                getReview(totalLectures.get(2), totalUsers.get(1), 3.0, "좋아요5", "스프링 개발 추천 강의2"),
                getReview(totalLectures.get(3), totalUsers.get(0), 2.5, "좋아요", "리액트 네이티브 개발 추천 강의1"),
                getReview(totalLectures.get(1), totalUsers.get(0), 2.0, "좋아요", "스프링 개발 추천 강의"));
        reviewRepository.saveAll(totalReviews);

        // when
        List<Long> ids = totalLectures.stream()
                .map(Lecture::getId)
                .toList();

        Map<Long, Long> reviewCount = lectureQueryRepository.findReviewCount(ids);

        // then
        Assertions.assertThat(reviewCount.size()).isEqualTo(5);
        Assertions.assertThat(reviewCount.get(totalLectures.get(0).getId())).isEqualTo(0);
        Assertions.assertThat(reviewCount.get(totalLectures.get(1).getId())).isEqualTo(1);
        Assertions.assertThat(reviewCount.get(totalLectures.get(2).getId())).isEqualTo(2);
        Assertions.assertThat(reviewCount.get(totalLectures.get(3).getId())).isEqualTo(1);
        Assertions.assertThat(reviewCount.get(totalLectures.get(4).getId())).isEqualTo(3);
    }

    @DisplayName("강의 북마크수 조회")
    @Test
    void findBookMarkCount() {

        // given
        List<Lecture> totalLectures = List.of(
                getLecture("스프링 부트 - 핵심 원리와 활용", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링부트", "실무에 필요한 스프링 부트는 이 강의 하나로 모두 정리해드립니다."),
                getLecture("스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,스프링MVC", "웹 애플리케이션을 개발할 때 필요한 모든 웹 기술을 기초부터 이해하고, 완성할 수 있습니다. 스프링 MVC의 핵심 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("스프링 DB 2편 - 데이터 접근 활용 기술", "99,000", "김영한", "프로그래밍", "웹", "스프링,DB", "백엔드 개발에 필요한 DB 데이터 접근 기술을 활용하고, 완성할 수 있습니다. 스프링 DB 접근 기술의 원리와 구조를 이해하고, 더 깊이있는 백엔드 개발자로 성장할 수 있습니다."),
                getLecture("배달앱 클론코딩 [with React Native]", "71,500", "조현영", "프로그래밍", "웹", "리액트 네이티브", "리액트 네이티브로 라이더용 배달앱을 만들어봅니다. 6년간 리액트 네이티브로 5개 이상의 앱을 만들고, 카카오 모빌리티에 매각한 개발자의 강의입니다."),
                getLecture("앨런 iOS 앱 개발 (15개의 앱을 만들면서 근본원리부터 배우는 UIKit) - MVVM까지", "205,700", "앨런(Allen)", "프로그래밍", "웹", "iOS", "탄탄한 신입 iOS개발자가 되기 위한 기본기 갖추기. 15개의 앱을 만들어 보면서 익히는.. iOS프로그래밍의 기초"));
        lectureRepository.saveAll(totalLectures);

        List<Users> totalUsers = List.of(
                saveUser("user1@gmail.com", "user1"),
                saveUser("user2@gmail.com", "user2"),
                saveUser("user3@gmail.com", "user3"));
        userRepository.saveAll(totalUsers);

        List<Bookmark> totalBookmarks = List.of(
                getBookMark(totalLectures.get(0), totalUsers.get(0)),
                getBookMark(totalLectures.get(0), totalUsers.get(1)),
                getBookMark(totalLectures.get(0), totalUsers.get(2)),
                getBookMark(totalLectures.get(2), totalUsers.get(1)),
                getBookMark(totalLectures.get(1), totalUsers.get(2)));
        bookmarkRepository.saveAll(totalBookmarks);

        // when
        List<Long> ids = totalLectures.stream()
                .map(Lecture::getId)
                .toList();
        Map<Long, Long> bookMarkCount = lectureQueryRepository.findBookmarkCount(ids);

        // then
        Assertions.assertThat(bookMarkCount.size()).isEqualTo(5);
        Assertions.assertThat(bookMarkCount.get(totalLectures.get(0).getId())).isEqualTo(3);
        Assertions.assertThat(bookMarkCount.get(totalLectures.get(1).getId())).isEqualTo(1);
        Assertions.assertThat(bookMarkCount.get(totalLectures.get(2).getId())).isEqualTo(1);
        Assertions.assertThat(bookMarkCount.get(totalLectures.get(3).getId())).isEqualTo(0);
        Assertions.assertThat(bookMarkCount.get(totalLectures.get(4).getId())).isEqualTo(0);
    }

    @DisplayName("강의 Scope 비로그인 조회 - 랜덤한 별점 4.0 이상 수강 후기들")
    @Test
    void findByHighScoresWithNotLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1 = getReview(lecture1, user1, 5.0, "강의력 좋은, ", "내용이 알찹니다!");
        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");
        Review review4 = getReview(lecture4, user1, 4.5, "강의력 좋은, 이해가 잘돼요", "내용이 알찹니다!");
        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");
        Review review7 = getReview(lecture7, user1, 2.5, "내용이 자세해요", "내용이 알찹니다!");
        Review review8 = getReview(lecture8, user1, 3.5, "강의력 좋은, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4,
                review5, review6, review7, review8,
                review9, review10
        ));

        // when
        List<LectureScopeListReadResponse.DetailReview> detailReviews = lectureQueryRepository.findByHighScores(null);

        // then
        assertThat(detailReviews)
                .hasSize(3)
                .extracting("id", "score")
                .contains(
                        tuple(review1.getId(), review1.getScore()),
                        tuple(review4.getId(), review4.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("강의 Scope 로그인 후 조회 - 랜덤한 별점 4.0 이상 수강 후기들")
    @Test
    void findByHighScoresWithLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1", "요리,프로그래밍");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1 = getReview(lecture1, user1, 5.0, "강의력 좋은, ", "내용이 알찹니다!");
        Review review2 = getReview(lecture2, user1, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, user2, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");
        Review review4 = getReview(lecture4, user1, 4.5, "강의력 좋은, 이해가 잘돼요", "내용이 알찹니다!");
        Review review5 = getReview(lecture5, user1, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, user1, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");
        Review review7 = getReview(lecture7, user1, 2.5, "내용이 자세해요", "내용이 알찹니다!");
        Review review8 = getReview(lecture8, user1, 3.5, "강의력 좋은, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, user1, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, user1, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4,
                review5, review6, review7, review8,
                review9, review10
        ));

        // when
        List<LectureScopeListReadResponse.DetailReview> detailReviews =
                lectureQueryRepository.findByHighScores(user1.getInterests());

        // then
        assertThat(detailReviews)
                .hasSize(2)
                .extracting("id", "score")
                .contains(
                        tuple(review1.getId(), review1.getScore()),
                        tuple(review9.getId(), review9.getScore())
                );
    }

    @DisplayName("강의 Scope 비로그인 조회 - 랜덤한 별점 4.0 이상 수강 탈퇴한 사용자의 후기들")
    @Test
    void findByHighScoresWithNotLoginAndNotUserReview() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));


        // 리뷰 생성
        Review review1 = getReview(lecture1, null, 5.0, "강의력 좋은, ", "내용이 알찹니다!");
        Review review2 = getReview(lecture2, null, 0.5, "도움이 안되었어요, ", "윽... 너무별로");
        Review review3 = getReview(lecture3, null, 1.0, "도움이 안되었어요, ", "소리가 안들리는데요");
        Review review4 = getReview(lecture4, null, 4.5, "강의력 좋은, 이해가 잘돼요", "내용이 알찹니다!");
        Review review5 = getReview(lecture5, null, 1.5, "매우 적극적, ", "내용이 알찹니다!");
        Review review6 = getReview(lecture6, null, 2.0, "매우 적극적, 도움이 많이 됐어요, ", "내용이 알찹니다!");
        Review review7 = getReview(lecture7, null, 2.5, "내용이 자세해요", "내용이 알찹니다!");
        Review review8 = getReview(lecture8, null, 3.5, "강의력 좋은, 듣기 좋은 목소리", "내용이 알찹니다!");
        Review review9 = getReview(lecture9, null, 4.0, "듣기 좋은 목소리", "내용이 알찹니다!");
        Review review10 = getReview(lecture10, null, 3.0, "보통이에요, ", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1, review2, review3, review4,
                review5, review6, review7, review8,
                review9, review10
        ));

        // when
        List<LectureScopeListReadResponse.DetailReview> detailReviews = lectureQueryRepository.findByHighScores(null);

        // then
        assertThat(detailReviews)
                .hasSize(3)
                .extracting("id", "score", "userName")
                .contains(
                        tuple(review1.getId(), review1.getScore(), "탈퇴한 사용자"),
                        tuple(review4.getId(), review4.getScore(), "탈퇴한 사용자"),
                        tuple(review9.getId(), review9.getScore(), "탈퇴한 사용자")
                );
    }

    @DisplayName("강의 Scope 관심분야 Null 조회 - 강의력 좋은 순")
    @Test
    void findByBestLecturesWithNotLogin() {
        // given

        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");
        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));

        // 리뷰 생성
        // 우선순위 2
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        // 우선순위 1
        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        // 우선순위 3
        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        // 우선순위 4
        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c,
                review4a, review4b, review4c,
                review7a, review7b, review7c,
                review8
        ));

        // when
        List<LectureScopeListReadResponse.DetailLecture> detailLectures =
                lectureQueryRepository.findByBestLectures(null);

        // then
        assertThat(detailLectures)
                .extracting("id", "title")
                .containsExactly(
                        tuple(lecture4.getId(), lecture4.getTitle()),
                        tuple(lecture1.getId(), lecture1.getTitle()),
                        tuple(lecture7.getId(), lecture7.getTitle()),
                        tuple(lecture8.getId(), lecture8.getTitle())
                );
    }

    @DisplayName("강의 Scope 조회 - 강의력 좋은 순")
    @Test
    void findByBestLecturesWithLogin() {
        // given
        // 사용자 생성
        Users user1 = saveUser("test1@test.com", "테스터1", "요리,프로그래밍");
        Users user2 = saveUser("test2@test.com", "테스터2");
        Users user3 = saveUser("test3@test.com", "테스터3");

        userRepository.saveAll(List.of(user1, user2, user3));

        // 강의 생성
        Lecture lecture1 = getLecture("웹 디자인의 기초", "프로그래밍");
        Lecture lecture2 = getLecture("자바 프로그래밍 실전 입문", "프로그래밍");
        Lecture lecture3 = getLecture("맛있는 파스타 요리", "요리");
        Lecture lecture4 = getLecture("게임 개발의 첫걸음", "게임");
        Lecture lecture5 = getLecture("그림으로 배우는 일본어", "디자인");
        Lecture lecture6 = getLecture("프론트엔드 개발 마스터 클래스", "프로그래밍");
        Lecture lecture7 = getLecture("컴퓨터 비전 기초", "프로그래밍");
        Lecture lecture8 = getLecture("디지털 아트 워크샵", "디자인");
        Lecture lecture9 = getLecture("영화 속 레시피", "요리");
        Lecture lecture10 = getLecture("모바일 게임 디자인", "디자인");
        Lecture lecture11 = getLecture("너무 쉬운 스프링 개발서", "프로그래밍");
        Lecture lecture12 = getLecture("파이썬 프로그래밍 기초", "프로그래밍");

        lectureRepository.saveAll(List.of(
                lecture1, lecture2, lecture3,
                lecture4, lecture5, lecture6,
                lecture7, lecture8, lecture9,
                lecture10, lecture11, lecture12));

        // 리뷰 생성
        // 우선순위 2
        Review review1a = getReview(lecture1, user1, 5.0, "이해가 잘돼요, ", "내용이 알찹니다!");
        Review review1b = getReview(lecture1, user2, 5.0, "뛰어난 강의력, ", "아주 퍼펙트한 강의 이군요");
        Review review1c = getReview(lecture1, user3, 5.0, "뛰어난 강의력, ", "이 강의를 듣고 개념이 달라졌습니다.");

        // 우선순위 1
        Review review4a = getReview(lecture4, user1, 4.5, "뛰어난 강의력, 이해가 잘돼요", "내용이 알찹니다!");
        Review review4b = getReview(lecture4, user2, 5.0, "뛰어난 강의력, 매우 적극적", "내용이 알찹니다!");
        Review review4c = getReview(lecture4, user3, 5.0, "뛰어난 강의력, ", "내용이 알찹니다!");

        // 우선순위 3
        Review review7a = getReview(lecture7, user1, 4.0, "내용이 자세해요", "내용이 알찹니다!");
        Review review7b = getReview(lecture7, user2, 3.5, "뛰어난 강의력, ", "내용이 알찹니다!");
        Review review7c = getReview(lecture7, user3, 4.0, "내용이 자세해요, ", "내용이 알찹니다!");

        // 우선순위 4
        Review review8 = getReview(lecture8, user1, 3.5, "뛰어난 강의력, 듣기 좋은 목소리", "내용이 알찹니다!");

        reviewRepository.saveAll(List.of(
                review1a, review1b, review1c,
                review4a, review4b, review4c,
                review7a, review7b, review7c,
                review8
        ));

        // when
        List<LectureScopeListReadResponse.DetailLecture> detailLectures =
                lectureQueryRepository.findByBestLectures(user1.getInterests());

        // then
        assertThat(detailLectures)
                .extracting("id", "title", "url")
                .hasSize(2)
                .containsExactly(
                        tuple(lecture1.getId(), lecture1.getTitle(), "URL"),
                        tuple(lecture7.getId(), lecture7.getTitle(), "URL")
                );
    }

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
                .source("출처")
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

    private static Bookmark getBookMark(Lecture lecture, Users user) {
        return Bookmark.builder()
                .lecture(lecture)
                .user(user)
                .build();
    }

    private static LikeReview getLikeReview(Review review, Users user) {
        return LikeReview.builder().review(review)
                .users(user)
                .build();
    }
}
