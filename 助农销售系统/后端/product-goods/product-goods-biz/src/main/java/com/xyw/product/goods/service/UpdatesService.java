package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.goods.entity.Updates;

import java.util.Map;

public interface UpdatesService extends IService<Updates> {

	int deleteByPrimaryKey(Long updateId);

	Map<String, Object> selectByBusinessId(Page page, Long businessId);

	int insert(Updates record);

	int insertSelective(Updates record);

	Updates selectByPrimaryKey(Long updateId);

	int updateByPrimaryKeySelective(Updates record);

	int updateByPrimaryKey(Updates record);

}
