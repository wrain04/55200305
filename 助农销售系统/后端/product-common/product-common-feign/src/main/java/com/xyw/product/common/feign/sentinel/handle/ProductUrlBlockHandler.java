package com.xyw.product.common.feign.sentinel.handle;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyw.product.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class ProductUrlBlockHandler implements BlockExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
		log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);

		response.setContentType(MediaType.APPLICATION_JSON.getType());
		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		response.getWriter().print(objectMapper.writeValueAsString(R.failed(e.getMessage())));
	}

}
