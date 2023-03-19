package com.totoblog.controller;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import com.totoblog.common.dto.CommonResponseDTO;
import com.totoblog.service.BlogService;
import com.totoblog.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
@Tag(name="Blog Controller", description="Blog 구현하기")
public class BlogController {

    private final BlogService blogService;
    private final KeywordService keywordService;

    @GetMapping("/blogs")
    @Operation(description = "블로그 조회하기")
    public CommonResponseDTO blogList(@NotNull @Valid BlogRecordReqDTO request){
        // 1. 검색어 키워드 저장하기
        keywordService.saveKeyword(request);

        // 2. 조회하기
        return CommonResponseDTO.of(blogService.blogClientService(request));
    }


}
