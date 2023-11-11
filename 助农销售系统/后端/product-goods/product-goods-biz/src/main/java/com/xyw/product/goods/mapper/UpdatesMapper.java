package com.xyw.product.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.goods.entity.Category;
import com.xyw.product.goods.entity.Updates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UpdatesMapper extends BaseMapper<Updates> {

	/**
	 * delete by primary key
	 * @param updateId primaryKey
	 * @return deleteCount
	 */
	int deleteByPrimaryKey(Long updateId);

	/**
	 * insert record to table
	 * @param record the record
	 * @return insert count
	 */
	int insert(Updates record);

	/**
	 * insert record to table selective
	 * @param record the record
	 * @return insert count
	 */
	int insertSelective(Updates record);

	/**
	 * select by primary key
	 * @param updateId primary key
	 * @return object by primary key
	 */
	Updates selectByPrimaryKey(Long updateId);

	/**
	 * update record selective
	 * @param record the updated record
	 * @return update count
	 */
	int updateByPrimaryKeySelective(Updates record);

	/**
	 * update record
	 * @param record the updated record
	 * @return update count
	 */
	int updateByPrimaryKey(Updates record);

}