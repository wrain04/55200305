package com.xyw.product.goods.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.goods.entity.TimeChart;
import com.xyw.product.goods.mapper.TimeChartMapper;
import com.xyw.product.goods.service.TimeChartService;
import com.xyw.product.goods.vo.TimeChartVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeChartServiceImpl extends ServiceImpl<TimeChartMapper, TimeChart> implements TimeChartService {

	@Override
	public List<TimeChartVO> getByGoodId(String goodId) {
		return baseMapper
				.selectList(Wrappers
						.<TimeChart>lambdaQuery().eq(TimeChart::getGoodId, goodId).orderByAsc(TimeChart::getTime))
				.stream()
				.map(i -> TimeChartVO.builder().goodId(i.getGoodId()).chartId(i.getChartId()).time(i.getTime())
						.description(i.getDescription()).imageList(JSONUtil.toList(i.getImages(), String.class))
						.build())
				.collect(Collectors.toList());
	}

}
