package dnd.project.domain.version.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
public enum Authority {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }
}
