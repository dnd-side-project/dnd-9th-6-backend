package dnd.project.domain.version.domain.lecture.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Lecture {

    @Id
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
    private String keyword;

    private String imageUrl;


}
