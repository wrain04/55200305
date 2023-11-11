package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.dto.BusinessDetailDTO;
import com.xyw.product.goods.entity.BusinessDetail;
import com.xyw.product.goods.vo.BusinessDetailVO;

import java.util.Map;

public interface BusinessDetailService extends IService<BusinessDetail> {

	Map<String, Object> getBusinessDetail(Long businessId);

	R uploadDetail(BusinessDetailDTO dto);

	BusinessDetailVO getSelfDetail();

	R deleteById(Long businessId);
}
