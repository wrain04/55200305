package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.dto.GoodsDTO;
import com.xyw.product.goods.entity.Good;
import com.xyw.product.goods.vo.GoodsVO;

import java.util.List;
import java.util.Map;

public interface GoodsService extends IService<Good> {

	Map<String, Object> getGoodsByBusinessId(Page page, Long businessId);

	Map<String, Object> getGoodsInfoByGradeId(Page page, String gradeId);

	List<GoodsVO> getGoodsInfoByRandom(Long n);

	Map<String, Object> getByBusinessId(Page page, Long businessId);

	R registerGoods(GoodsDTO dto);

	R updateGoods(GoodsDTO dto);

	GoodsVO getByIdToVO(String goodId);

	List<GoodsVO> getByIdListToVO(List<String> goodIdList);

	boolean isExist(String goodId);

}
