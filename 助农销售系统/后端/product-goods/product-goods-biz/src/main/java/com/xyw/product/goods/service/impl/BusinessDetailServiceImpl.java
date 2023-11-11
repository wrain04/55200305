package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.dto.BusinessDetailDTO;
import com.xyw.product.goods.entity.BusinessDetail;
import com.xyw.product.goods.mapper.BusinessDetailMapper;
import com.xyw.product.goods.service.BusinessDetailService;
import com.xyw.product.goods.vo.BusinessDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.xyw.product.common.core.constant.ProductConstants.BUSINESS_REVIEW;
import static com.xyw.product.common.core.constant.ProductConstants.BUSINESS_USED;

@Service
@Slf4j
public class BusinessDetailServiceImpl extends ServiceImpl<BusinessDetailMapper, BusinessDetail>
		implements BusinessDetailService {


	@Override
	public Map<String, Object> getBusinessDetail(Long businessId) {
		return BeanUtil.beanToMap(BeanUtil.toBean(getBaseMapper().selectOne(Wrappers.<BusinessDetail>lambdaQuery()
						.eq(BusinessDetail::getBusinessId, businessId).eq(BusinessDetail::getLockFlag, BUSINESS_USED)
						.select(BusinessDetail::getBusinessId, BusinessDetail::getStoreName, BusinessDetail::getBusinessLogo,
								BusinessDetail::getBusinessType, BusinessDetail::getDescription,
								BusinessDetail::getMainProducts, BusinessDetail::getBusinessPhone, BusinessDetail::getAddress)),
				BusinessDetailVO.class), false, true);
	}

	@Override
	public R uploadDetail(BusinessDetailDTO dto) {
		BusinessDetail businessDetail = BeanUtil.copyProperties(dto, BusinessDetail.class, "licenseList");
		businessDetail.setLicense(JSONUtil.toJsonStr(dto.getLicenseList()));
		businessDetail.setLockFlag(BUSINESS_REVIEW.toString());
		return (baseMapper.selectCount(Wrappers.<BusinessDetail>lambdaQuery().eq(BusinessDetail::getBusinessId, dto.getBusinessId())) < 1 ? save(businessDetail) : dto.getBusinessId() != null && updateById(businessDetail)) ? R.ok() : R.failed();
	}

	@Override
	public BusinessDetailVO getSelfDetail() {
		BusinessDetail selected = getBaseMapper().selectOne(Wrappers.<BusinessDetail>lambdaQuery()
				.eq(BusinessDetail::getBusinessId, SecurityUtils.getUser().getId()));
		if (selected == null)
			return null;
		BusinessDetailVO ret = BeanUtil.copyProperties(selected, BusinessDetailVO.class);
		ret.setLicenseList(JSONUtil.toList(selected.getLicense(), String.class));
		return ret;
	}

	@Override
	public R deleteById(Long businessId) {
		return null;
	}

}