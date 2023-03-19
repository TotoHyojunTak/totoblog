package com.totoblog.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Blog 검색 조회 조건")
public class BlogReqDTO {

    /*** 검색을 원하는 질의어 ***/
    private String query;

    /*** 결과 문서 정렬 방식 ***/
    @Schema(defaultValue="accuracy")
    private String sort;

    /*** 결과 페이지 번호 ***/
    @Schema(defaultValue="1")
    private Integer page;

    /*** 한 페이지에 보여질 문서 수 ***/
    @Schema(defaultValue="50")
    private Integer size;

}