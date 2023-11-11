package com.xyw.product.goods.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.common.core.constant.ProductConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.entity.Statistics;
import com.xyw.product.goods.mapper.StatisticsMapper;
import com.xyw.product.goods.service.CollectionService;
import com.xyw.product.goods.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

	private final RedisTemplate<String, String> redisTemplate;

	private final CollectionService collectionService;

	@Override
	public boolean createByBusinessId(DateTime time, Long businessId) {
		remove(Wrappers.<Statistics>lambdaQuery().eq(Statistics::getBusinessId, businessId)
				.between(Statistics::getCreateTime, DateUtil.beginOfDay(time), DateUtil.endOfDay(time)));
		return save(new Statistics().setBusinessId(businessId));
	}

	@Override
	public R updateOrderSumByBusinessId(Long businessId) {
		DateTime now = DateUtil.date();
		if (checkData(now, businessId)) {
			return R.failed("操作异常");
		}
		return R.ok(update(Wrappers.<Statistics>lambdaUpdate().eq(Statistics::getBusinessId, businessId)
				.between(Statistics::getCreateTime, DateUtil.beginOfDay(now), DateUtil.endOfDay(now))
				.setSql("order_num=order_num+" + 1)));
	}

	@Override
	public R updateDateViewByBusinessId(Long businessId, String goodsId, String userId) {
		if (!"".equals(userId)) {
			collectionService.collectHistory(Long.parseLong(userId), goodsId);
		}
		DateTime now = DateUtil.date();
		if (checkData(now, businessId)) {
			return R.failed("操作异常");
		}
		return R.ok(update(Wrappers.<Statistics>lambdaUpdate().eq(Statistics::getBusinessId, businessId)
				.between(Statistics::getCreateTime, DateUtil.beginOfDay(now), DateUtil.endOfDay(now))
				.setSql("date_view=date_view+" + 1)));
	}

	@Override
	public Map<String, Object> getInfo(Long businessId) {
		Map<String, Object> map;
		String json;
		if ((json = redisTemplate.opsForValue().get(ProductConstants.STATISTICS_INFO_BUSINESS + businessId)) != null) {
			map = JSONUtil.toBean(json, HashMap.class);
			return map;
		}
		map = new HashMap<>();
		map.put("userId", SecurityUtils.getUser().getId());
		DateTime yesterday = DateUtil.yesterday();
		DateTime today = DateUtil.date();
		Statistics yesterdayData = baseMapper
				.selectOne(Wrappers.<Statistics>lambdaQuery().eq(Statistics::getBusinessId, businessId)
						.between(Statistics::getCreateTime, DateUtil.beginOfDay(yesterday),
								DateUtil.endOfDay(yesterday))
						.select(Statistics::getDateView, Statistics::getOrderNum, Statistics::getDeal));
		Statistics todayData = baseMapper
				.selectOne(Wrappers.<Statistics>lambdaQuery().eq(Statistics::getBusinessId, businessId)
						.between(Statistics::getCreateTime, DateUtil.beginOfDay(today), DateUtil.endOfDay(today))
						.select(Statistics::getDateView, Statistics::getOrderNum, Statistics::getDeal));
		if (yesterdayData == null) {
			yesterdayData = new Statistics().setDateView(0).setDeal(new BigDecimal("0")).setOrderNum(0);
		}
		if (todayData == null) {
			todayData = new Statistics().setDateView(0).setDeal(new BigDecimal("0")).setOrderNum(0);
		}
		map.put("viewSum", new Integer[] { yesterdayData.getDateView(), todayData.getDateView() });
		map.put("orderNum", new Integer[] { yesterdayData.getOrderNum(), todayData.getOrderNum() });
		map.put("deal", new String[] { yesterdayData.getDeal().toString(), todayData.getDeal().toString() });
		buildChart(businessId, map);
		redisTemplate.opsForValue().set(ProductConstants.STATISTICS_INFO_BUSINESS + businessId, JSONUtil.toJsonStr(map),
				ProductConstants.STATISTICS_INFO_BUSINESS_TIMEOUT, ProductConstants.STATISTICS_INFO_BUSINESS_TIMEUNIT);
		return map;
	}

	private void buildChart(Long businessId, Map<String, Object> map) {
		HashMap<String, Object> chart = new HashMap<>();
		ArrayList<String> categories = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		DateTime offset = DateUtil.offset(DateUtil.date(), DateField.MONTH, -6);
		for (int i = 0; i < 6; i++) {
			offset = DateUtil.offset(offset, DateField.MONTH, 1);
			BigDecimal decimal = baseMapper.selectToChart(businessId, DateUtil.beginOfMonth(offset),
					DateUtil.endOfMonth(offset));
			data.add(decimal == null ? "0" : decimal.toString());
			categories.add(DateUtil.format(offset, "MM"));
		}
		chart.put("data", data);
		chart.put("categories", categories);
		map.put("chart", chart);
	}

	private boolean checkData(DateTime now, Long businessId) {
		if (!getBaseMapper().exists(Wrappers.<Statistics>lambdaQuery().eq(Statistics::getBusinessId, businessId)
				.between(Statistics::getCreateTime, DateUtil.beginOfDay(now), DateUtil.endOfDay(now)))) {
			return !createByBusinessId(now, businessId);
		}
		return false;
	}

}