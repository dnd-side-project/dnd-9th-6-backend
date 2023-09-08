package dnd.project.docs.exception;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.exception.ExceptionController;
import dnd.project.domain.exception.ExceptionHandler;
import dnd.project.global.common.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static dnd.project.global.common.Result.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionControllerDocsTest extends RestDocsSupport {

    private final ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);

    @Override
    protected Object initController() {
        return new ExceptionController(exceptionHandler);
    }

    @DisplayName("기본 에러코드 문서")
    @Test
    void commonExceptionDocs() throws Exception {
        // given
        given(exceptionHandler.commonException())
                .willReturn("강의 ID는 필수입니다.");

        ResourceSnippetParameters parameters = ResourceSnippetParameters.builder()
                .tag("예외처리 예시")
                .summary("Parameter Exception")
                .description("""
                        상태 코드(고정) / 상태 메세지 \s
                        \s
                        -1, "강의 ID는 필수입니다."
                        """
                )
                .responseFields(
                        fieldWithPath("code").type(NUMBER).description("상태 코드"),
                        fieldWithPath("message").type(STRING).description("상태 메세지"))
                .build();

        RestDocumentationResultHandler document =
                documentHandler("exception1", prettyPrint(), parameters);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/common-exception")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document);
    }

    @DisplayName("커스텀 에러코드 문서")
    @Test
    void customExceptionDocs() throws Exception {
        // given
        given(exceptionHandler.customException())
                .willReturn(NOT_FOUND_USER);

        ResourceSnippetParameters parameters = ResourceSnippetParameters.builder()
                .tag("예외처리 예시")
                .summary("Custom Exception")
                .description("""
                        상태 코드 / 상태 메시지 \s
                        \s
                        -1000, "존재하지 않는 사용자" \s
                        -1001, "하나 이상의 관심분야를 선택해야 합니다." \s
                        \s
                        -2000, "존재하지 않는 강의" \s
                        -2001, "존재하지 않는 카테고리" \s
                        -2002, "존재하지 않는 메인 카테고리" \s
                        -2003, "존재하지 않는 서브 카테고리" \s
                        \s
                        -3000, "이미 해당 강의의 후기를 작성하였습니다." \s
                        -3001, "존재하지 않는 리뷰" \s
                        -3002, "후기를 작성한 사람이 아닙니다." \s
                        -3003, "내가 작성한 후기에는 좋아요를 남길 수 없습니다." \s
                        -3004, "아직 후기를 한번도 작성하지 않았습니다." \s
                        """
                )
                .responseFields(
                        fieldWithPath("code").type(NUMBER).description("상태 코드"),
                        fieldWithPath("message").type(STRING).description("상태 메세지"))
                .build();

        RestDocumentationResultHandler document =
                documentHandler("exception2", prettyPrint(), parameters);

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/custom-exception")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document);
    }
}
