package dnd.project.global.common.exception;

import dnd.project.global.common.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

	private Result result;
	private String debug;

	public CustomException(Result result) {
		this.result = result;
		this.debug = result.getMessage();
	}
}
