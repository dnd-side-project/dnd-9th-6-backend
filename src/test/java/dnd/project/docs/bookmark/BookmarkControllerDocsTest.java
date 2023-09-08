package dnd.project.docs.bookmark;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import dnd.project.docs.RestDocsSupport;
import dnd.project.domain.bookmark.controller.BookmarkController;
import dnd.project.domain.bookmark.response.BookmarkResponse;
import dnd.project.domain.bookmark.service.BookmarkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookmarkControllerDocsTest extends RestDocsSupport {

    private final BookmarkService bookmarkService = mock(BookmarkService.class);

    @Override
    protected Object initController() {
        return new BookmarkController(bookmarkService);
    }

    @DisplayName("북마크 등록 API")
    @Test
    void addBookmark() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/auth/bookmark")
                                .header(AUTHORIZATION, "Bearer {token}")
                                .param("lectureId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("add-bookmark",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("북마크 API")
                                .summary("북마크 등록 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Swagger 요청시 해당 입력칸이 아닌 우측 상단 자물쇠 " +
                                                        "또는 Authorize 버튼을 이용해 토큰을 넣어주세요"))
                                .formParameters(
                                        parameterWithName("lectureId")
                                                .description("강의 ID"))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("상태 메세지"))
                                .build())));
    }

    @DisplayName("북마크 취소 API")
    @Test
    void cancelBookmark() throws Exception {
        // given
        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/auth/bookmark")
                                .header(AUTHORIZATION, "Bearer {token}")
                                .param("lectureId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("cancel-bookmark",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("북마크 API")
                                .summary("북마크 취소 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Swagger 요청시 해당 입력칸이 아닌 우측 상단 자물쇠 " +
                                                        "또는 Authorize 버튼을 이용해 토큰을 넣어주세요"))
                                .formParameters(
                                        parameterWithName("lectureId")
                                                .description("강의 ID"))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("상태 메세지"))
                                .build())));
    }

    @DisplayName("내 북마크 조회 API")
    @Test
    void readMyBookmark() throws Exception {
        // given
        BookmarkResponse.Detail response1 = BookmarkResponse.Detail.builder()
                .bookmarkId(1L)
                .lectureId(3L)
                .lectureImageUrl("https://fastcampus.co.kr/dev_online_fe100")
                .name("박세문,마광휘")
                .source("fastcampus")
                .title("100가지 시나리오로 학습하는 프론트엔드 : 5년 이상 경험을 초압축한 실전 문제 해결 패키지")
                .price("211000")
                .addedDate("2023-08-02")
                .build();

        BookmarkResponse.Detail response2 = BookmarkResponse.Detail.builder()
                .bookmarkId(2L)
                .lectureId(12L)
                .lectureImageUrl("https://fastcampus.co.kr/dev_online_besignature")
                .name("박매일,예상국,김하은,김민수,이형구,이성미,양재현,문혜림,류호석")
                .source("fastcampus")
                .title("시그니처 백엔드 Path 초격차 패키지 Online.")
                .price("216500")
                .addedDate("2023-08-07")
                .build();

        BookmarkResponse.Detail response3 = BookmarkResponse.Detail.builder()
                .bookmarkId(3L)
                .lectureId(372L)
                .lectureImageUrl("https://i.namu.wiki/i/mx41tN5qbVkjI2r-2aQsQczq11b-Y4YJ5jYE0gw2g6OjqGN35Y9zCbc4S6PfJ_tnp_eLb9JUo1y7Df_F6-Q8EcOJZjWkzQLT9goeJ-xlRTTCPI6p4L_XrkzaFk3biGcxKh0ytgUyVP5micOAUB7YvQ.webp")
                .name("Jack Grealish")
                .source("coloso")
                .title("영국 왕실에서만 사용하는 비밀 디저트 레시피 오픈")
                .price("725000")
                .addedDate("2023-08-14")
                .build();

        given(bookmarkService.readMyBookmark(any()))
                .willReturn(List.of(response1, response2, response3));

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/auth/bookmark")
                                .header(AUTHORIZATION, "Bearer {token}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("read-myBookmark",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("북마크 API")
                                .summary("내 북마크 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("Swagger 요청시 해당 입력칸이 아닌 우측 상단 자물쇠 " +
                                                        "또는 Authorize 버튼을 이용해 토큰을 넣어주세요"))
                                .responseFields(
                                        fieldWithPath("code").type(NUMBER)
                                                .description("상태 코드"),
                                        fieldWithPath("message").type(STRING)
                                                .description("상태 메세지"),
                                        fieldWithPath("data[].bookmarkId").type(NUMBER)
                                                .description("북마크 ID"),
                                        fieldWithPath("data[].lectureId").type(NUMBER)
                                                .description("강의 ID"),
                                        fieldWithPath("data[].lectureImageUrl").type(STRING)
                                                .description("강의 이미지 URL"),
                                        fieldWithPath("data[].name").type(STRING)
                                                .description("강사 이름"),
                                        fieldWithPath("data[].source").type(STRING)
                                                .description("강의 플랫폼"),
                                        fieldWithPath("data[].title").type(STRING)
                                                .description("강의 제목"),
                                        fieldWithPath("data[].price").type(STRING)
                                                .description("강의 가격"),
                                        fieldWithPath("data[].addedDate").type(STRING)
                                                .description("북마크 등록 날짜"))
                                .build())));
    }
}
