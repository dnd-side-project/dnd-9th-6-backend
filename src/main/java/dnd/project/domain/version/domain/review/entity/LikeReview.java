package dnd.project.domain.version.domain.review.entity;

import dnd.project.domain.version.domain.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class LikeReview {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

}
