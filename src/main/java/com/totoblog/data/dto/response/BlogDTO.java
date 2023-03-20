package com.totoblog.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Setter @Getter
@Builder
@Schema(name = "Blog 검색 통합 결과")
public class BlogDTO {
    /**
     * 블로그 검색 결과
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    public static class Response {

        private Meta meta;
        private List<Documents> documents;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    public static class Meta {

        /*** 검색된 문서 수 ***/
        private Integer totalCount;
        /**
         * 현재 페이지가 마지막 페이지인지 여부.
         * 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
         */
        private Boolean isEnd;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    public static class Documents {

        /*** 블로그 글 제목 ***/
        private String title;

        /*** 블로그 글 요약 ***/
        private String contents;

        /*** 블로그 글 URL ***/
        private String url;

        /*** 블로그의 이름 ***/
        private String blogname;

        /*** 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경 ***/
        private String thumbnail;

        /*** 블로그 글 작성일자 ***/
        private LocalDate registrationDate;

    }

    /*** Kakao 결과 매핑(webclient) ***/
    public static Response webClientKakaoOf(BlogKakaoDTO.KakaoResponse res) {
        final BlogDTO.Meta meta = kakaoOf(res.getMeta());

        final List<BlogDTO.Documents> documentList = res.getDocuments()
                .stream()
                .map(BlogDTO::kakaoOf)
                .collect(Collectors.toList());
        return Response.of(meta, documentList);
    }

    /*** Naver 결과 매핑(webclient) ***/
    public static Response webClientNaverOf(BlogNaverDTO.NaverResponse res) {
        final BlogDTO.Meta meta = naverOfMeta(res.getStart(), res);

        final List<BlogDTO.Documents> documentList = res.getItems()
                .stream()
                .map(BlogDTO::naverOf)
                .collect(Collectors.toList());
        return Response.of(meta, documentList);
    }

    /*** Kakao 결과 매핑(restTemplate) ***/
    public static Response kakaoOf(final BlogKakaoDTO.KakaoResponse response) {
        final BlogDTO.Meta meta = kakaoOf(response.getMeta());
        final List<BlogDTO.Documents> documentList = response.getDocuments()
                .stream()
                .map(BlogDTO::kakaoOf)
                .collect(Collectors.toList());
        return Response.of(meta, documentList);
    }

    private static BlogDTO.Documents kakaoOf(final BlogKakaoDTO.Documents documents) {
        return BlogDTO.Documents.of(
                documents.getTitle(),
                documents.getContents(),
                documents.getUrl(),
                documents.getBlogname(),
                documents.getThumbnail(),
                documents.getDatetime() == null? null: documents.getDatetime().toLocalDate()
        );
    }

    public static BlogDTO.Meta kakaoOf(final BlogKakaoDTO.Meta meta) {
        return BlogDTO.Meta.of(meta.getTotalCount(), meta.getIsEnd());
    }

    /*** Naver 결과 매핑 ***/
    public static Response naverOf(final Integer start, final BlogNaverDTO.NaverResponse response) {
        final BlogDTO.Meta meta = naverOfMeta(start, response);
        final List<BlogDTO.Documents> documentList = response.getItems()
                .stream()
                .map(BlogDTO::naverOf)
                .collect(Collectors.toList());
        return Response.of(meta, documentList);
    }

    private static Meta naverOfMeta(final Integer start, final BlogNaverDTO.NaverResponse response) {
        return Meta.of(response.getTotal(), response.isEnd(start));
    }

    private static BlogDTO.Documents naverOf(final BlogNaverDTO.Item document) {
        return BlogDTO.Documents.of(
                document.getTitle(),
                document.getDescription(),
                document.getLink(),
                document.getBloggername(),
                null,
                document.getPostdate()
        );
    }

}