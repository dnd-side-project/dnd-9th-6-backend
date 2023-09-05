package dnd.project.domain.exception;

import dnd.project.global.common.Result;
import org.springframework.stereotype.Service;

import static dnd.project.global.common.Result.*;

@Service
public class ExceptionHandler {
    public String commonException() {
        return "강의 ID는 필수입니다.";
    }

    public Result customException() {
        return NOT_FOUND_USER;
    }
}
