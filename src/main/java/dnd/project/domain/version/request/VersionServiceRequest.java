package dnd.project.domain.version.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VersionServiceRequest {

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String version;
        private String updateAdminName;

        @Builder
        private Update(String version, String updateAdminName) {
            this.version = version;
            this.updateAdminName = updateAdminName;
        }
    }
}
