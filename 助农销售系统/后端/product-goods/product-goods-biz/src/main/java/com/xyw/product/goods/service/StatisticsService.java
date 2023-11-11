package com.xyw.product.goods.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.entity.Statistics;

import java.util.Map;

public interface StatisticsService extends IService<Statistics> {

	boolean createByBusinessId(DateTime time, Long businessId);

	R updateOrderSumByBusinessId(Long businessId);

	R updateDateViewByBusinessId(Long businessId, String GoodsId, String userId);

	Map<String, Object> getInfo(Long businessId);

}
