package com.xyw.product.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.admin.api.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 * @return 数据列表
	 */
	List<SysDept> listDepts();

}
