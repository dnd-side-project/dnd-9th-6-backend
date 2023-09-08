package dnd.project.domain.exception;

import dnd.project.global.common.CustomResponseEntity;
import dnd.project.global.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExceptionController {

    private final ExceptionHandler exceptionHandler;

    @DeleteMapping("/common-exception")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseEntity<String> commonException () {
        return CustomResponseEntity.fail(exceptionHandler.commonException());
    }

    @DeleteMapping("/custom-exception")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseEntity<Result> customException () {
        return CustomResponseEntity.fail(exceptionHandler.customException());
    }
}
