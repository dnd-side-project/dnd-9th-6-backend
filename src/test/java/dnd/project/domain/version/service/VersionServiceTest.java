package dnd.project.domain.version.service;

import dnd.project.domain.version.domain.version.request.VersionRequest;
import dnd.project.domain.version.domain.version.response.VersionResponse;
import dnd.project.domain.version.domain.version.service.VersionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VersionServiceTest {
    @Autowired
    private VersionService versionService;

    @DisplayName("관리자가 서비스의 버전을 변경한다.")
    @Test
    void updateServiceVersion() {
        // given
        VersionRequest.Update request = new VersionRequest.Update("0.0.2", "TEST");

        // when
        VersionResponse.Update update =
                versionService.updateServiceVersion(request.toServiceRequest());

        // then
        assertThat(update)
                .extracting("version", "updateAdminName")
                .contains("0.0.2", "TEST");
    }

}