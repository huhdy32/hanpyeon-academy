package com.hanpyeon.academyapi.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.question.QuestionService;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestionController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BoardMapper boardMapper;
    @MockBean
    QuestionService questionService;

    // targetMemberId가 null 일 수 있도록 요구사항 변경됨에 따라 테스트 안함
    void 질문_등록시_targetMemberId_없음_에러_테스트() throws Exception {
        mockMvc.perform(multipart("/api/board/questions")
                .param("content", "내용")
        ).andExpect(status().isBadRequest());
    }

    /**
     * no more used
     */
//    @Test
//    void 질문_등록시_content_없음_테스트() throws Exception {
//        mockMvc.perform(multipart("/api/board/questions")
//                .param("targetMemberId", "1")
//        ).andExpect(status().isBadRequest());
//    }

    @Test
    void 이미지포함_질문등록_성공_테스트() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());
        mockMvc.perform(multipart("/api/board/questions")
                .file(image)
                .param("title", "제목")
                .param("content", "내용")
        ).andExpect(status().isCreated());
    }

    @Test
    void 이미지_없음_성공_테스트() throws Exception {
        mockMvc.perform(multipart("/api/board/questions")
                        .param("targetMemberId", "1")
                        .param("content", "hello")
                ).andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void 이미지_존재_성공_테스트() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "images",
                "helwijadw".getBytes());

        mockMvc.perform(multipart("/api/board/questions")
                .file(image)
                .param("content", "내용")
                .param("targetMemberId", "12")
        ).andExpect(status().isCreated());
    }

    @Test
    void 여러_이미지_성공_테스트() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());
        MockMultipartFile image2 = new MockMultipartFile(
                "image",
                "helwijadw".getBytes());

        mockMvc.perform(multipart("/api/board/questions")
                .file(image1)
                .file(image2)
                .param("content", "내용")
                .param("targetMemberId", "12")
        ).andExpect(status().isCreated());
    }

    @Test
    void 질문조회성공테스트() throws Exception {
        Mockito.when(questionService.getSingleQuestionDetails(Mockito.any()))
                .thenReturn(Mockito.mock(QuestionDetails.class));
        mockMvc.perform(get("/api/board/questions/12"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @ParameterizedTest
    @CsvSource({
            "10",
            "5",
            "15"
    })
    void 페이지_조회_테스트(String pageSize) throws Exception {
        mockMvc.perform(get("/api/board/questions")
                        .param("size", pageSize)
                        .param("cursorIndex", "12"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void 질문_삭제_API_테스트() throws Exception {
        mockMvc.perform(delete("/api/board/questions/1"))
                .andExpect(status().isNoContent());
    }
}
