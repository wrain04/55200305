package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.admin.api.feign.RemoteFileService;
import com.xyw.product.goods.entity.Category;
import com.xyw.product.goods.vo.CategoryVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

	List<CategoryVO> getCategoryParentInfo();

	List<CategoryVO> searchCategoryInfo(String categoryName);

	List<CategoryVO> getCategoryInfo(String parentId, RemoteFileService fileService);

}
