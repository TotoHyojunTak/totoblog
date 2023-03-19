package com.totoblog.controller;

import com.totoblog.common.dto.CommonResponseDTO;
import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.service.BlogService;
import com.totoblog.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
@Slf4j
@Tag(name="Blog Controller", description="Blog 구현하기")
public class BlogController {

    private final BlogService blogService;
    private final KeywordService keywordService;

    @GetMapping("/blogs")
    @Operation(description = "블로그 조회하기")
    public CommonResponseDTO blogList(@NotNull @Valid BlogReqDTO request){

        // 0. DTO Builder
        BlogReqDTO dto = BlogReqDTO.builder()
                .query(request.getQuery())
                .sort(request.getSort())
                .size(request.getSize())
                .page(request.getPage())
                .build();

        if (!StringUtils.hasText(dto.getQuery())) {
            throw new IllegalArgumentException("keyword - empty");
        }
        if (Objects.nonNull(dto.getPage()) && (dto.getPage() < 1 || dto.getPage() > 50)) {
            throw new IllegalArgumentException("page - invalid & wrong number (1~50)");
        }
        if (Objects.nonNull(dto.getSize()) && (dto.getSize() < 1 || dto.getSize() > 50)) {
            throw new IllegalArgumentException("size - invalid & wrong number (1~50)");
        }


        // 1. 검색어 키워드 저장하기
        keywordService.saveKeyword(dto);

        // 2. 조회하기
        return CommonResponseDTO.of(blogService.blogClientService(dto));
    }


}
