package com.totoblog.service;


import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import com.totoblog.service.component.ApiCallerTemplate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final ApiCallerTemplate template;

    // 블로그 검색하기
    public BlogDTO.Response blogClientService(@NotNull @Valid BlogReqDTO request) {
        return template.getList(request);
    }
}
