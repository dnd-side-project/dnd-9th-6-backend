package dnd.project.domain.version.domain.version.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VersionRequest {

    @Getter
    @NoArgsConstructor
    public static class Update {
        @NotBlank(message = "버전은 필수 입력 값 입니다.")
        private String version;
        @NotBlank(message = "관리자 이름은 필수 입력 값 입니다.")
        private String updateAdminName;

        // 테스트 생성자
        public Update(String version, String updateAdminName) {
            this.version = version;
            this.updateAdminName = updateAdminName;
        }

        public VersionServiceRequest.Update toServiceRequest() {
            return VersionServiceRequest.Update.builder()
                    .version(version)
                    .updateAdminName(updateAdminName)
                    .build();
        }
    }
}
