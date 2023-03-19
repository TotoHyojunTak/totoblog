package com.totoblog.service;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import com.totoblog.data.dto.response.KeywordDTO;
import com.totoblog.data.entity.Keyword;
import com.totoblog.data.repository.KeywordRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeywordService {

    private final KeywordRepository keywordRepository;

    /*** 최고 많이 검색한 키워드 10개 검색하기 ***/
    public List<KeywordDTO> getBest10KeywordList() {
        /*** jpql - dto 수동 매핑 ***/
        return keywordRepository.getBest10KeywordList().stream().map(temp -> KeywordDTO.builder()
                .keyword(temp.getKeyword())
                .cnt(temp.getCnt())
                .build()).collect(Collectors.toList());
    }

    /*** 키워드 조회 시, 저장하기 ***/
    public void saveKeyword(@NotNull @Valid BlogRecordReqDTO request) {
        Keyword keyword = new Keyword(request.query());

        keywordRepository.save(keyword);
    }
}
