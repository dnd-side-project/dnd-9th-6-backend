package dnd.project.domain.review.entity;

import dnd.project.domain.BaseEntity;
import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.user.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @NotNull
    private Double score;

    @NotNull
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "review", fetch = LAZY)
    @Builder.Default
    private List<LikeReview> likeReviews = new ArrayList<>();

    public void toUpdateReview(Double score, String tags, String content) {
        this.score = score;
        this.tags = tags;
        this.content = content;
    }
}
