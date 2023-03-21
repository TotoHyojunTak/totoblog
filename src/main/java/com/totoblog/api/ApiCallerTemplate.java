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

	private final List<ApiCaller> providers;

	/*** ApiCaller를 상속받은 서비스사 한번에 조회가능하도록 상속처리
	 *** 하지만, 서비스 마다 Request & Response 형식이 다르기 때문에 최종 결과에 맞게 매핑 필요
	 *+*/

	public BlogDTO.Response getList(@NotNull @Valid BlogReqDTO request) {
		for (ApiCaller provider : providers) {
			try {
				return provider.list(request);
			} catch (Exception e) {
				log.warn("Client {} - {}", provider.getClass().getName(), e);
			}
		}
		throw new RuntimeException("블로그 검색 API 최종 실패.");
	}
}
