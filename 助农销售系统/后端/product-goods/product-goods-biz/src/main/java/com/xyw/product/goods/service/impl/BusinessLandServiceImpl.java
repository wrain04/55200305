package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.dto.BusinessLandDTO;
import com.xyw.product.goods.entity.BusinessLand;
import com.xyw.product.goods.mapper.BusinessLandMapper;
import com.xyw.product.goods.service.BusinessLandService;
import com.xyw.product.goods.vo.BusinessLandVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessLandServiceImpl extends ServiceImpl<BusinessLandMapper, BusinessLand>
		implements BusinessLandService {

	@Override
	public Boolean addLand(BusinessLandDTO dto) {
		BusinessLand businessLand = BeanUtil.copyProperties(dto, BusinessLand.class);
		businessLand.setLandImage(JSONUtil.toJsonStr(dto.getLandImageList()));
		return save(businessLand);
	}

	@Override
	public List<BusinessLandVO> getLands() {
		return getBaseMapper().selectList(
				Wrappers.<BusinessLand>lambdaQuery().eq(BusinessLand::getBusinessId, SecurityUtils.getUser().getId()))
				.stream().map(l -> {
					BusinessLandVO vo = BeanUtil.copyProperties(l, BusinessLandVO.class);
					vo.setLandImageList(JSONUtil.toList(l.getLandImage(), String.class));
					return vo;
				}).collect(Collectors.toList());
	}

}
