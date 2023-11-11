package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.goods.dto.BusinessLandDTO;
import com.xyw.product.goods.entity.BusinessLand;
import com.xyw.product.goods.vo.BusinessLandVO;

import java.util.List;

public interface BusinessLandService extends IService<BusinessLand> {

	Boolean addLand(BusinessLandDTO dto);

	List<BusinessLandVO> getLands();

}
