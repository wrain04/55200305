package com.xyw.product.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.order.dto.OrderDTO;
import com.xyw.product.order.entity.Order;
import com.xyw.product.order.vo.OrderVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrdersService extends IService<Order> {

	/**
	 * 用户下单
	 * @param order
	 * @return
	 */
	Long createOrder(OrderDTO dto);

	List<OrderVO> getOrderByStatus(Long status, boolean isUser);

	/**
	 * 用户获取自己的待发货数量
	 * @return
	 */
	Long getWaitSend(Long id);

	/**
	 * 用户获取自己的获取待退款/退货数量
	 * @return
	 */
	Long getWaitRefundORReturn(Long id);

	Long getWaitRefundORReturnToBusiness(Long id);

	Long getWaitSendToBusiness(Long id);

	/**
	 * 根据订单id查询订单
	 * @param orderId
	 * @return
	 */
	OrderVO getOrderByOrderId(Long orderId);

	/**
	 * 获取所有订单
	 * @return
	 */
	Map<String, Object> getAllOrder();

}
