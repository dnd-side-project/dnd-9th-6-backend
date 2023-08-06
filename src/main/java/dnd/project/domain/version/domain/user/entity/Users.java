package dnd.project.domain.version.domain.user.entity;

import dnd.project.domain.version.domain.bookmark.entity.Bookmark;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private String Interests;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Bookmark> bookmarks;
}
