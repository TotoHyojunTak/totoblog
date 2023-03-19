package com.totoblog.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(name = "Blog 검색 조회 조건")
public record BlogRecordReqDTO(String query, String sort, Integer page, Integer size) {
    public BlogRecordReqDTO {
        Objects.requireNonNull(query);
        Objects.requireNonNull(sort);
        Objects.requireNonNull(page);
        Objects.requireNonNull(size);
    }

}