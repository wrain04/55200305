package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.goods.entity.Updates;
import com.xyw.product.goods.mapper.UpdatesMapper;
import com.xyw.product.goods.service.UpdatesService;
import com.xyw.product.goods.vo.UpdatesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UpdatesServiceImpl extends ServiceImpl<UpdatesMapper, Updates> implements UpdatesService {

	@Resource
	private UpdatesMapper updatesMapper;

	@Override
	public int deleteByPrimaryKey(Long updateId) {
		return updatesMapper.deleteByPrimaryKey(updateId);
	}

	@Override
	public Map<String, Object> selectByBusinessId(Page page, Long businessId) {
		Page selectPage = updatesMapper.selectPage(page,
				Wrappers.<Updates>lambdaQuery().eq(Updates::getBusinessId, businessId));
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", page.getTotal());
		map.put("list", page.getRecords().stream().map(u -> BeanUtil.copyProperties(u, UpdatesVO.class))
				.collect(Collectors.toList()));
		return map;
	}

	@Override
	public int insert(Updates record) {
		return updatesMapper.insert(record);
	}

	@Override
	public int insertSelective(Updates record) {
		return updatesMapper.insertSelective(record);
	}

	@Override
	public Updates selectByPrimaryKey(Long updateId) {
		return updatesMapper.selectByPrimaryKey(updateId);
	}

	@Override
	public int updateByPrimaryKeySelective(Updates record) {
		return updatesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Updates record) {
		return updatesMapper.updateByPrimaryKey(record);
	}

}
