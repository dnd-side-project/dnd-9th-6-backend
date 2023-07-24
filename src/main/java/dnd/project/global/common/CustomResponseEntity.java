package dnd.project.global.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponseEntity<T> {

    private int code;
    private String message;
    private T data;

    public static <T> CustomResponseEntity<T> success(T data) {
        return CustomResponseEntity.<T>builder()
                .result(Result.OK)
                .data(data)
                .build();
    }

    public static <T> CustomResponseEntity<T> success() {
        return CustomResponseEntity.<T>builder()
                .result(Result.OK)
                .build();
    }

    public static <T> CustomResponseEntity<T> fail(String message) {
        return CustomResponseEntity.<T>builder()
                .code(Result.FAIL.getCode())
                .message(message)
                .build();
    }

    public static <T> CustomResponseEntity<T> fail(Result result) {
        return CustomResponseEntity.<T>builder()
                .result(result)
                .build();
    }

    @Builder
    public CustomResponseEntity(Result result, int code, String message, T data) {
        this.code = (result == null) ? code : result.getCode();
        this.message = (result == null) ? message : result.getMessage();
        this.data = data;
    }

}
