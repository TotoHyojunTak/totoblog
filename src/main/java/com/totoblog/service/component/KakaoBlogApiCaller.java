package com.totoblog.service.component;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import com.totoblog.data.dto.response.BlogKakaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

    public BlogDTO.Response list(final BlogRecordReqDTO request) {
//        final HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", auth + " " + key);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

//        URI uri = UriComponentsBuilder.fromHttpUrl(url + path)
//                .queryParam("query", request.query())
//                .queryParam("sort", request.sort())
//                .queryParam("page", request.page())
//                .queryParam("size", request.size())
//                .build()
//                .toUri();
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(path)
                .queryParam("query", request.query())
                .queryParam("sort", request.sort())
                .queryParam("page", request.page())
                .queryParam("size", request.size())
                .encode()
                .build().toUri();

        RequestEntity<Void> res = RequestEntity
                .get(uri)
                .header("Authorization", auth + " " + key)
                .build()
                ;


        final ResponseEntity<BlogKakaoDTO.KakaoResponse> resEntity = restTemplate.exchange(res, BlogKakaoDTO.KakaoResponse.class);
        log.debug("uri={}, res={}, statusCode={}, responseBody={}", uri, res, resEntity.getStatusCode(), resEntity.getBody());

        if (!resEntity.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException("[실패] 카카오 API 통신 실패");
        }

        return BlogDTO.kakaoOf(resEntity.getBody());
        //return BlogMapper.INSTANCE.toDtoForKakao(resEntity.getBody());
    }

}
