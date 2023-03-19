package com.totoblog.service.component;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import com.totoblog.data.dto.response.BlogNaverDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaverBlogApiCaller {
    private final RestTemplate restTemplate;

    @Value("${api.naver.url}")
    private String url;
    @Value("${api.naver.client-id}")
    private String clientId;
    @Value("${api.naver.client-secret}")
    private String clientSecret;

    @Value("${api.kakao.path}")
    private String path;

    public BlogDTO.Response list(final BlogRecordReqDTO request) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(path)
                .encode()
                .build().toUri();

        RequestEntity<BlogRecordReqDTO> res = RequestEntity
                .post(uri)
                .contentType(MediaType.ALL)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .body(request)
                ;

        final ResponseEntity<BlogNaverDTO.NaverResponse> resEntity = restTemplate.exchange(
                res, BlogNaverDTO.NaverResponse.class);
        log.debug("uri={}, res={}, statusCode={}, responseBody={}", uri, res, resEntity.getStatusCode(), resEntity.getBody());

        if (!resEntity.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException("[실패] 네이버 API 통신 실패");
        }
        return BlogDTO.naverOf(resEntity.getBody().getStart(), resEntity.getBody());
    }

}
