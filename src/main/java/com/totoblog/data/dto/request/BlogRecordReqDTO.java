package com.totoblog.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Schema(name = "Blog 검색 조회 조건")
public record BlogRecordReqDTO(String query, String sort, Integer page, Integer size) {
    public BlogRecordReqDTO {
        Objects.requireNonNull(query);
        Objects.requireNonNull(sort);
        Objects.requireNonNull(page);
        Objects.requireNonNull(size);
    }

    public void validate() {
        // 필수값 검증
        if (!StringUtils.hasText(query)) {
            throw new IllegalArgumentException("keyword - empty");
        }
        if (Objects.nonNull(page) && (page < 1 || page > 50)) {
            throw new IllegalArgumentException("page - invalid & wrong number");
        }
        if (Objects.nonNull(size) && (size < 1 || size > 50)) {
            throw new IllegalArgumentException("size - invalid & wrong number");
        }
    }

}