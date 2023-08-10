package dnd.project.domain.user.entity;

import dnd.project.domain.bookmark.entity.Bookmark;
import dnd.project.domain.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String imageUrl;

    @NotNull
    private String nickName;

    private String interests;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    public void toUpdateInterests(String interests) {
        this.interests = interests;
    }
}
