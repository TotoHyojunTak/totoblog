package com.totoblog.service.component;

import com.totoblog.data.dto.request.BlogReqDTO;
import com.totoblog.data.dto.response.BlogDTO;

public interface ApiCaller {
	BlogDTO.Response list(BlogReqDTO request);
}
