package com.xyw.product.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.admin.api.entity.SysPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPostMapper extends BaseMapper<SysPost> {

	/**
	 * 通过用户ID，查询岗位信息
	 * @param userId 用户id
	 * @return 岗位信息
	 */
	List<SysPost> listPostsByUserId(Long userId);

}
