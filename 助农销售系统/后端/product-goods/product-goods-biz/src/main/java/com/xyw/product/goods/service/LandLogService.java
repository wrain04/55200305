package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.goods.entity.LandLog;

import java.util.Map;

public interface LandLogService extends IService<LandLog> {

	public Map<String, Object> getBySelf(String landId);

}
