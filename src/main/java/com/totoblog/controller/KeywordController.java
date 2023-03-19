package com.totoblog.controller;

import com.totoblog.common.dto.CommonResponseDTO;
import com.totoblog.data.dto.response.KeywordDTO;
import com.totoblog.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
@Tag(name="Keyword Controller", description="keyword 구현하기")
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/keywords")
    @Operation(description = "상위 10개 키워드 조회하기")
    public CommonResponseDTO getBest10KeywordList(){
        return CommonResponseDTO.of(keywordService.getBest10KeywordList());
    }


}
