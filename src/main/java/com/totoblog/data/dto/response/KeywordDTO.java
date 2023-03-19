package com.totoblog.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Keyword 결과")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDTO {
    private String keyword;
    private Integer cnt;
}