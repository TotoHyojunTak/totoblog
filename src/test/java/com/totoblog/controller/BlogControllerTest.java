package com.totoblog.controller;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("블로그 검색하기")
    @Disabled
    void test_blogList() throws Exception {
        // given
        String keyword = "여행";
        String sort = "accuracy";
        Integer page = 1;
        Integer size = 50;

        // when & then

        mockMvc.perform(MockMvcRequestBuilders.get("/blog/blogs")
                        .param("query", keyword)
                        .param("sort", sort)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.code").exists())
                            .andExpect(jsonPath("$.message").exists())
                            .andExpect(jsonPath("$.data.meta.totalCount").exists())
                            .andExpect(jsonPath("$.data.meta.isEnd").exists())
                            .andExpect(jsonPath("$.data.documents[0].title").exists())
                            .andExpect(jsonPath("$.data.documents[0].contents").exists())
                            .andExpect(jsonPath("$.data.documents[0].url").exists())
                            .andExpect(jsonPath("$.data.documents[0].blogname").exists())
                            .andExpect(jsonPath("$.data.documents[0].thumbnail").exists())
                            .andExpect(jsonPath("$.data.documents[0].registrationDate").exists())
                            .andDo(print());
    }
}