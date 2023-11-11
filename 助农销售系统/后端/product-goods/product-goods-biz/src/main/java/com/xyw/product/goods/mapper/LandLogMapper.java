package com.xyw.product.goods.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.goods.entity.LandLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface LandLogMapper extends BaseMapper<LandLog> {

	BigDecimal selectToChart(@Param("landId") Long landId, @Param("begin") DateTime begin, @Param("end") DateTime end);

}
