package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.goods.entity.TimeChart;
import com.xyw.product.goods.vo.TimeChartVO;

import java.util.List;

public interface TimeChartService extends IService<TimeChart> {

	List<TimeChartVO> getByGoodId(String goodId);

}
