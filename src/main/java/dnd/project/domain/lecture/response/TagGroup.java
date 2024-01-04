package dnd.project.domain.lecture.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TagGroup {
    private final String name;
    private final List<Tag> tags;

    @Getter
    @RequiredArgsConstructor
    public static class Tag {
        private final String name;
        private final Integer count;
    }
}
