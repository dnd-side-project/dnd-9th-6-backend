package dnd.project.global.common.exception;

import dnd.project.global.common.CustomResponseEntity;
import dnd.project.global.common.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CommonRestExceptionHandler extends RuntimeException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public CustomResponseEntity<String> handleExceptionHandler(HttpServletRequest request, Exception e) {
        log.error("defaultExceptionHandler", e);
        return CustomResponseEntity.fail(Result.FAIL);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public CustomResponseEntity<String> handleCustomExceptionHandler(CustomException exception) {
        log.error("CustomExceptionHandler code : {}, message : {}",
                exception.getResult().getCode(), exception.getResult().getMessage());
        return CustomResponseEntity.fail(exception.getResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public CustomResponseEntity<String> illegalArgumentExceptionHandler(
            IllegalArgumentException e, HttpServletRequest request
    ) {
        log.error("url: \"{}\", message: {}", request.getRequestURI(), e.getMessage());

        return CustomResponseEntity.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public CustomResponseEntity<Object> handleBadRequest(
            MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("url: \"{}\", message: {}", request.getRequestURI(), errorMessage);

        return CustomResponseEntity.fail(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            MissingServletRequestParameterException.class
    )
    public CustomResponseEntity<String> handleBadRequest(
            MissingServletRequestParameterException e, HttpServletRequest request
    ) {
        String errorMessage = e.getParameterName() + " 값이 등록되지 않았습니다.";
        log.error("url: \"{}\", message: {}", request.getRequestURI(), errorMessage);

        return CustomResponseEntity.fail(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            MissingServletRequestPartException.class
    )
    public CustomResponseEntity<String> handleBadRequest(
            MissingServletRequestPartException e, HttpServletRequest request
    ) {
        String errorMessage = e.getRequestPartName() + " 값을 요청받지 못했습니다.";
        log.error("url: \"{}\", message: {}", request.getRequestURI(), errorMessage);

        return CustomResponseEntity.fail(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public CustomResponseEntity<String> nullPointerExceptionHandler(
            Exception e, HttpServletRequest request
    ) {
        log.error("url: \"{}\", message: {}", request.getRequestURI(), e.getMessage());

        return CustomResponseEntity.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            {SecurityException.class, MalformedJwtException.class}
    )
    public CustomResponseEntity<String> securityExceptionHandler(
            SecurityException e, HttpServletRequest request
    ) {
        log.error("잘못된 JWT 서명입니다. | url: \"{}\", message: {}", request.getRequestURI(), e.getMessage());

        return CustomResponseEntity.fail("잘못된 JWT 서명입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public CustomResponseEntity<String> expiredJwtExceptionHandler(
            ExpiredJwtException e, HttpServletRequest request
    ) {
        String errorMessage = "만료된 JWT 토큰입니다.";
        log.error("url: \"{}\", message: {}", request.getRequestURI(), errorMessage);

        return CustomResponseEntity.fail(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedJwtException.class)
    public CustomResponseEntity<String> unsupportedJwtExceptionHandler(
            ExpiredJwtException e, HttpServletRequest request
    ) {
        String errorMessage = "지원되지 않는 JWT 토큰입니다.";
        log.error("url: \"{}\", message: {}", request.getRequestURI(), errorMessage);

        return CustomResponseEntity.fail(errorMessage);
    }

}