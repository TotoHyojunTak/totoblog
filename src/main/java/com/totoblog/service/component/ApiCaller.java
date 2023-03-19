package com.totoblog.service.component;

import com.totoblog.data.dto.request.BlogRecordReqDTO;
import com.totoblog.data.dto.response.BlogDTO;

import java.util.List;

public interface ApiCaller {


	BlogDTO.Response list(BlogRecordReqDTO request);
}
