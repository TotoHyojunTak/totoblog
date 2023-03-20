package com.totoblog.service.component;

import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import com.totoblog.data.dto.response.BlogNaverDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaverBlogApiCaller implements ApiCaller {
    private final RestTemplate restTemplate;

    @Value("${api.naver.url}")
    private String url;

    @Value("${api.naver.client-id}")
    private String clientId;

    @Value("${api.naver.client-secret}")
    private String clientSecret;

    @Value("${api.naver.path}")
    private String path;

    public BlogDTO.Response list(final BlogReqDTO request) {

        /*
            외부와의 통신은 RestTemplate, WebClient 등 다양한 방법이 있다.
            주로, 위 두 가지를 많이 사용하고 있는데
            RestTemplate은 스프링 5 버전부터 사용하는 것을 지양하고
            5부터 등장한 WebClient 사용을 권장한다.

            위 2가지 방법으로 모두 구현하였으며 테스트 종료 후, RestTemplate으로 구현한 부분은 주석 처리하였다.
         */

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
//
//        RequestEntity<Void> res = RequestEntity
//                .get(uri)
//                .header("X-Naver-Client-Id", clientId)
//                .header("X-Naver-Client-Secret", clientSecret)
//                .build()
//                ;
//
//        final ResponseEntity<BlogNaverDTO.NaverResponse> resEntity = restTemplate.exchange(
//                res, BlogNaverDTO.NaverResponse.class);
//        log.debug("uri={}, res={}, statusCode={}, responseBody={}", uri, res, resEntity.getStatusCode(), resEntity.getBody());
//
//        if (!resEntity.getStatusCode().is2xxSuccessful()) {
//            throw new RestClientException("[실패] 네이버 API 통신 실패");
//        }
//        return BlogDTO.naverOf(resEntity.getBody().getStart(), resEntity.getBody());

        // CASE2. WebClient
        WebClient webClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                        .responseTimeout(Duration.ofMillis(20000))
                        .proxyWithSystemProperties())) // 외부 API 서버 호출
                .baseUrl(url)
                .defaultHeaders(headers -> {
                    headers.set("X-Naver-Client-Id", clientId);
                    headers.set("X-Naver-Client-Secret", clientSecret);
                })
                .build();

        BlogNaverDTO.NaverResponse client = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("query", request.getQuery())
                        .queryParam("sort", request.getSort() == "accuracy"? "sim": "date")
                        .queryParam("start", request.getPage())
                        .queryParam("display", request.getSize())
                        .build())
                .retrieve()
                .bodyToMono(BlogNaverDTO.NaverResponse.class)
                .block()
                ;

        return BlogDTO.webClientNaverOf(client); // webclient
    }

}
