package com.xyw.product.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyw.product.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Order> {

	/**
	 * 插入订单数据
	 * @param order 订单数据
	 * @return 受影响的行数
	 */
	Integer insertOrder(Order order);

	/**
	 * 根据订单id查询订单
	 * @param orderId
	 * @return
	 */
	Order getOrderByOrderId(Long orderId);

	/**
	 * 获取所有订单
	 * @return
	 */
	List<Order> getAllOrder();

}
