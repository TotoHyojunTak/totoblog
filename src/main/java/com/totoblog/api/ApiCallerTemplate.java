package com.totoblog.api;

import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiCallerTemplate {

	private final List<ApiCaller> providerCaller;

	public BlogDTO.Response getList(@NotNull @Valid BlogReqDTO request) {
		for (ApiCaller client : providerCaller) {
			try {
				return client.list(request);
			} catch (Exception e) {
				log.warn("Client {} failed {}", client.getClass().getName(), e);
			}
		}
		throw new RuntimeException("블로그 검색 API 최종 실패.");
	}
}
