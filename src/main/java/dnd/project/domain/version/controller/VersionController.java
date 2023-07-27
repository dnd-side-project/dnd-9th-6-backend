package dnd.project.domain.version.controller;

import dnd.project.domain.version.request.VersionRequest;
import dnd.project.domain.version.response.VersionResponse;
import dnd.project.domain.version.service.VersionService;
import dnd.project.global.common.CustomResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping("/api/v1/version")
    public CustomResponseEntity<String> checkVersion() {
        return CustomResponseEntity.success("Release 0.0.5");
    }

    @PostMapping("/api/v1/version")
    public CustomResponseEntity<VersionResponse.Update> updateServiceVersion(
           @Valid @RequestBody VersionRequest.Update request
    ) {
        return CustomResponseEntity.success(versionService.updateServiceVersion(request.toServiceRequest()));
    }
}
