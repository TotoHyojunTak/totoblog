package com.totoblog.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import com.totoblog.data.dto.response.BlogKakaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoBlogApiCaller implements ApiCaller {

    private final RestTemplate restTemplate;

    @Value("${api.kakao.url}")
    private String url;

    @Value("${api.kakao.authorization}")
    private String auth;

    @Value("${api.kakao.key}")
    private String key;

    @Value("${api.kakao.path}")
    private String path;

    public BlogDTO.Response list(final BlogReqDTO request) throws WebClientResponseException, JsonProcessingException {

        // 네이버 테스트를 위한 강제 Exception 처리 (주석)
        // throw new RestClientException("[실패] 카카오 API 통신 실패");

        // CASE1. RestTemplate 버전 (주석 처리)
//        URI uri = UriComponentsBuilder
//                .fromUriString(url)
//                .path(path)
//                .queryParam("query", request.getQuery())
//                .queryParam("sort", request.getSort())
//                .queryParam("page", request.getPage())
//                .queryParam("size", request.getSize())
//                .encode()
//                .build().toUri();

//        RequestEntity<Void> res = RequestEntity
//                .get(uri)
//                .header("Authorization", auth + " " + key)
//                .build()
//                ;
//
//        final ResponseEntity<BlogKakaoDTO.KakaoResponse> resEntity = restTemplate.exchange(res, BlogKakaoDTO.KakaoResponse.class);
//        log.debug("uri={}, res={}, statusCode={}, responseBody={}", uri, res, resEntity.getStatusCode(), resEntity.getBody());
//        if (!resEntity.getStatusCode().is2xxSuccessful()) {
//            throw new RestClientException("[실패] 카카오 API 통신 실패");
//        }
//        return BlogDTO.kakaoOf(resEntity.getBody()); // restTemplate


        // CASE2. WebClient
        WebClient webClient = WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", auth + " " + key)
                .build();

        BlogKakaoDTO.KakaoResponse  client = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                                .path(path)
                        .queryParam("query", request.getQuery())
                        .queryParam("sort", request.getSort())
                        .queryParam("page", request.getPage())
                        .queryParam("size", request.getSize())
                        .build())
                .retrieve()
                .bodyToMono(BlogKakaoDTO.KakaoResponse.class)
                .block()
                ;

        return BlogDTO.webClientKakaoOf(client); // webclient
    }

}
