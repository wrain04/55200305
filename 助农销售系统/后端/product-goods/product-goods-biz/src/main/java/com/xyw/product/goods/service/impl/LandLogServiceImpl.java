package com.xyw.product.goods.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.goods.entity.LandLog;
import com.xyw.product.goods.mapper.LandLogMapper;
import com.xyw.product.goods.service.LandLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class LandLogServiceImpl extends ServiceImpl<LandLogMapper, LandLog> implements LandLogService {

	@Override
	public Map<String, Object> getBySelf(String landId) {
		HashMap<String, Object> chart = new HashMap<>();
		ArrayList<String> categories = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		DateTime offset = DateUtil.offset(DateUtil.date(), DateField.MONTH, -6);

		for (int i = 0; i < 6; i++) {
			offset = DateUtil.offset(offset, DateField.MONTH, 1);
			BigDecimal decimal = baseMapper.selectToChart(Long.parseLong(landId), DateUtil.beginOfMonth(offset),
					DateUtil.endOfMonth(offset));
			data.add(decimal == null ? "0" : decimal.toString());
			categories.add(DateUtil.format(offset, "MM"));
		}
		chart.put("data", data);
		chart.put("categories", categories);
		return chart;
	}

}
