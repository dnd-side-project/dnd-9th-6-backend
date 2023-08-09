package dnd.project.domain.lecture.entity;

import dnd.project.domain.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String source;

    @NotNull
    private String url;

    @NotNull
    private String price;

    @NotNull
    private String name;

    @NotNull
    private String mainCategory;

    @NotNull
    private String subCategory;

    @NotNull
    private String keywords;

    private String content;

    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Review> reviews;

}
