package dnd.project.domain.version.domain.version.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VersionResponse {

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

        public static Update response(String version, String updateAdminName) {
            return Update.builder()
                    .version(version)
                    .updateAdminName(updateAdminName)
                    .build();
        }
    }
}
