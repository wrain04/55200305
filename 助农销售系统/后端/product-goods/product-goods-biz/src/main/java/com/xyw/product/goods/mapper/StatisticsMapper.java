package com.xyw.product.goods.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.goods.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {

	BigDecimal selectToChart(@Param("businessId") Long businessId, @Param("begin") DateTime begin,
			@Param("end") DateTime end);

}
