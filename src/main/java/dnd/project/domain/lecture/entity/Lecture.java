package dnd.project.domain.lecture.entity;

import dnd.project.domain.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Lecture {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private String imageUrl;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public String getFormattedPrice() {
        String priceString = this.price.replaceAll("\\D", "");
        try {
            DecimalFormat df = new DecimalFormat("###,###");
            long price = Long.parseLong(priceString);
            return df.format(price) + "원";
        } catch (Exception NumberFormatException) {
            return "0";
        }
    }
}
