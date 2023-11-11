package com.xyw.product.goods.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.plugin.oss.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {

	@Autowired
	public OssProperties ossProperties;

}
