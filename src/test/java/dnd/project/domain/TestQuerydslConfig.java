package dnd.project.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.project.domain.lecture.repository.LectureQueryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@RequiredArgsConstructor
public class TestQuerydslConfig {
    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public LectureQueryRepository lectureQueryRepository() {
        return new LectureQueryRepository(queryFactory());
    }
}
