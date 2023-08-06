package dnd.project.domain.version.domain.bookmark.entity;

import dnd.project.domain.version.domain.lecture.entity.Lecture;
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
public class Bookmark {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
