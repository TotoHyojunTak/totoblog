package com.totoblog.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;

public interface ApiCaller {
	BlogDTO.Response list(BlogReqDTO request) throws JsonProcessingException;
}
