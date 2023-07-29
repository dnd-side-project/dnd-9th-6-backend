package dnd.project.domain.version.domain.version.service;

import dnd.project.domain.version.domain.version.response.VersionResponse;
import dnd.project.domain.version.domain.version.request.VersionServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class VersionService {
    public VersionResponse.Update updateServiceVersion(VersionServiceRequest.Update serviceRequest) {
        return VersionResponse.Update.response(
                serviceRequest.getVersion(), serviceRequest.getUpdateAdminName()
        );
    }
}
