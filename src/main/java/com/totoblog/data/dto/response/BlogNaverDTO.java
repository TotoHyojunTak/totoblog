package com.totoblog.data.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogNaverDTO{
    /**
     * 블로그 검색 결과
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    public static class NaverResponse {

        private Integer total;
        private Integer start;
        private Integer display;
        private List<Item> items;

        public Boolean isEnd(final Integer start) {
            return start >= total;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @ToString
    public static class Item {

        /*** 블로그 포스트의 제목 ***/
        private String title;

        /*** 블로그 포스트의 내용을 요약한 패시지 정보 ***/
        private String description;

        /*** 블로그 포스트의 URL ***/
        private String link;

        /*** 블로그 포스트가 있는 블로그의 이름 ***/
        private String bloggername;

        /*** 블로그 포스트가 작성된 날짜 ***/
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
        private LocalDate postdate;

    }
}