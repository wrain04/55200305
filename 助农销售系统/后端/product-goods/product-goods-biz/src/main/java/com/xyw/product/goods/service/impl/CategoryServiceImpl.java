package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.goods.entity.Category;
import com.xyw.product.goods.mapper.CategoryMapper;
import com.xyw.product.goods.service.CategoryService;
import com.xyw.product.goods.vo.CategoryVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements CategoryService {

	static Random random = new Random();

	/**
	 * @param :
	 * @return List<CategoryVO>
	 * @author 徐志炫
	 * @description 获取一级分类
	 * @date 2022/10/28 15:15
	 */
	@Override
	public List<CategoryVO> getCategoryParentInfo() {
		return baseMapper.selectList(Wrappers.<Category>lambdaQuery().isNull(Category::getParentId)).stream()
				.map(bean -> {
					CategoryVO vo = new CategoryVO();
					BeanUtil.copyProperties(bean, vo);
					return vo;
				}).collect(Collectors.toList());
	}

	/**
	 * @param categoryName: 分类名
	 * @return List<CategoryVO>
	 * @author 徐志炫
	 * @description TODO 需要对接Elasticsearch
	 * @date 2023/1/17 13:19
	 */
	@Override
	public List<CategoryVO> searchCategoryInfo(String categoryName) {
		return baseMapper
				.selectList(Wrappers.<Category>lambdaQuery().like(categoryName != null, Category::getName, categoryName)
						.isNotNull(Category::getParentId).isNotNull(Category::getFileName).select(Category::getCategoryId, Category::getName))
				.stream().map(bean -> {
					CategoryVO vo = new CategoryVO();
					BeanUtil.copyProperties(bean, vo);
					return vo;
				}).collect(Collectors.toList());
	}

	/**
	 * @param parentId:
	 * @return List<CategoryVO>
	 * @author 徐志炫
	 * @description 获取子分类，并且拼接logo路径
	 * @date 2022/10/28 15:15
	 */
	@Override
	public List<CategoryVO> getCategoryInfo(String parentId, RemoteFileService fileService) {
		return baseMapper.selectList(Wrappers.<Category>query().lambda().eq(Category::getParentId, parentId)).stream()
				.map(bean -> {
					CategoryVO vo = new CategoryVO();
					BeanUtil.copyProperties(bean, vo);
					vo.setLogo(bean.getFileName());
					return vo;
				}).collect(Collectors.toList());
	}

}
