package com.xyw.product.admin.api.feign;

import com.xyw.product.admin.api.entity.SysDictItem;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(contextId = "remoteDictService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDictService {

	/**
	 * 通过字典类型查找字典
	 * @param type 字典类型
	 * @return 同类型字典
	 */
	@GetMapping("/dict/type/{type}")
	R<List<SysDictItem>> getDictByType(@PathVariable("type") String type);

}
