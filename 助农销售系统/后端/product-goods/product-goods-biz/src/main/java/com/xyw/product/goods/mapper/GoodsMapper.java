package com.xyw.product.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.goods.entity.Good;
import com.xyw.product.goods.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Good> {

	// TODO 优化算法
	List<Good> selectGoodsInfoByRandom(@Param("n") Long n);

	/**
	 * @param businessId:
	 * @return List<GoodsVO>
	 * @author 徐志炫
	 * @description TODO 未施工
	 * @date 2023/4/16 17:55
	 */
	List<GoodsVO> getByBusinessId(@Param("businessId") Long businessId);

}
